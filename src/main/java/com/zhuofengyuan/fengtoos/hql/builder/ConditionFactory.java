package com.zhuofengyuan.fengtoos.hql.builder;

import com.zhuofengyuan.fengtoos.hql.base.DefaultCondition;
import com.zhuofengyuan.fengtoos.hql.base.ICondition;
import com.zhuofengyuan.fengtoos.hql.base.IQuery;
import com.zhuofengyuan.fengtoos.hql.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConditionFactory {
//	protected Log logger = LogFactory.getLog(this.getClass());
	protected StringBuffer whereBuf = new StringBuffer();
	protected List<ICondition> fieldValues = new ArrayList<ICondition>();
	private int group = 0;
	protected String lastOper = "";

	public void and() {
		this.lastOper = " and ";
	}

	protected void append(String hql, boolean defaultLogic) {
		if (defaultLogic && this.lastOper.equals("")) {
			this.lastOper = " and ";
		}

		this.checkGroup();
		if (!this.whereBuf.toString().equals("")) {
			this.whereBuf.append(this.lastOper);
		}

		this.whereBuf.append(hql);
		this.lastOper = "";
	}

	protected String att2FName(String attribute) {
		return attribute;
	}

	private void checkGroup() {
		if (this.group == 1 || this.group == 2) {
			if (this.whereBuf.toString().trim().equalsIgnoreCase("")) {
				this.whereBuf.append("(");
			} else {
				this.lastOper = "";
				this.whereBuf.append(" and (");
			}

			this.group = 3;
		}

	}

	public void createCondtionGroup() {
		this.createCondtionGroup(false);
	}

	public void createCondtionGroup(boolean isOR) {
		if (isOR) {
			this.group = 2;
		} else {
			this.group = 1;
		}

	}

	public void endGroudCondtion() {
		if (this.group == 3) {
			this.append(")", false);
		}

		this.group = 0;
	}

	protected String getOperation(int queryAction) {
		String oper = "";
		switch (queryAction) {
			case 0 :
				oper = "=?";
				break;
			case 1 :
				oper = " like ? ";
				break;
			case 2 :
				oper = ">=?";
				break;
			case 3 :
				oper = "<=?";
				break;
			case 4 :
				oper = " in (?)";
				break;
			case 5 :
				oper = "<>?";
				break;
			case 6 :
				oper = ">?";
				break;
			case 7 :
				oper = "<?";
				break;
			case 8 :
				oper = " between ? and ? ";
				break;
			case 9 :
				oper = " not in (?)";
				break;
			case 101 :
				oper = " like ? ";
				break;
			case 102 :
				oper = " like ? ";
		}

		return oper;
	}

	public String getWhere() {
		this.endGroudCondtion();
		String tmp = this.whereBuf.toString();
		if (!tmp.equals("")) {
			tmp = " where " + tmp;
		}

//		this.logger.info("QueryBuilder.getWhere:" + tmp);
		return tmp;
	}

	public void or() {
		this.lastOper = " or ";
	}

	protected void where(String hql) {
		if (hql != null && !hql.trim().equals("")) {
			this.append(hql, true);
		}
	}

	protected void where(String fieldName, Date value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			if (value != null) {
				String oper = this.getOperation(queryAction);
				if (!oper.equals("")) {
					this.append(this.att2FName(fieldName) + oper, true);
					this.fieldValues.add(new DefaultCondition(value, false, 91));
				}

			}
		}
	}

	protected void where(String fieldName, double value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			Double v = new Double(value);
			String oper = this.getOperation(queryAction);
			if (!oper.equals("")) {
				this.append(this.att2FName(fieldName) + oper, true);
				this.fieldValues.add(new DefaultCondition(v, false, 8));
			}

		} else {
			throw new QueryBuilderException("where:浮点型数据不支持" + queryAction + "运算");
		}
	}

	protected void where(String fieldName, Double value, int queryAction) {
		if (value != null) {
			this.where(fieldName, value.doubleValue(), queryAction);
		}
	}

	protected void where(String fieldName, float value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			Float v = new Float(value);
			String oper = this.getOperation(queryAction);
			if (!oper.equals("")) {
				this.append(this.att2FName(fieldName) + oper, true);
				this.fieldValues.add(new DefaultCondition(v, false, 6));
			}

		}
	}

	protected void where(String fieldName, Float value, int queryAction) {
		if (value != null) {
			this.where(fieldName, value.floatValue(), queryAction);
		}
	}

	protected void where(String fieldName, ICondition condition, int queryAction) {
		if (fieldName != null && !fieldName.trim().equals("")) {
			if (condition.getValue() != null) {
				if (condition.getType() == 12) {
					String tmp = (String) condition.getValue();
					if (tmp.trim().equals("")) {
						return;
					}
				}

				this.append(this.att2FName(fieldName) + this.getOperation(queryAction), true);
				this.fieldValues.add(condition);
			}
		}
	}

	protected void where(String fieldName, int value) {
		this.where(fieldName, value, 0);
	}

	protected void where(String fieldName, int value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			Integer v = Integer.valueOf(value);
			String oper = this.getOperation(queryAction);
			if (!oper.equals("")) {
				this.append(this.att2FName(fieldName) + oper, true);
				this.fieldValues.add(new DefaultCondition(v, false, 4));
			}

		}
	}
	
	protected void where(String fieldName, Object value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			String oper = this.getOperation(queryAction);
			if (!oper.equals("")) {
				this.append(this.att2FName(fieldName) + oper, true);
				this.fieldValues.add(new DefaultCondition(value, false, 4));
			}

		}
	}
	
	protected void where(String fieldName, Object value) {
		this.where(fieldName, value, 0);
	}
	
	protected void where(String fieldName, int[] values) {
		this.where(fieldName, values, 4);
	}

	protected void where(String fieldName, int[] values, int action) {
		if (action != 4 && action != 9) {
//			this.logger.warn("where(int[]):parameters error!");
		} else if (values != null && values.length > 0) {
			StringBuffer stmp = new StringBuffer();

			for (int i = 0; i < values.length; ++i) {
				if (i == 0) {
					stmp.append(String.valueOf(values[i]));
				} else {
					stmp.append("," + String.valueOf(values[i]));
				}
			}

			this.append(this.att2FName(fieldName) + " in (" + stmp.toString() + ")", true);
		}

	}

	protected void where(String fieldName, Integer value, int queryAction) {
		if (value != null) {
			this.where(fieldName, value.intValue(), queryAction);
		}
	}

	protected void where(String field, IQuery subQuery) {
		String sql = String.format(" %s in (%s )", new Object[]{field, subQuery.getHQL()});
		this.append(sql, true);
		this.fieldValues.addAll(subQuery.getValues());
	}

	protected void where(String fieldName, long value) {
		this.where(fieldName, value, 0);
	}

	protected void where(String fieldName, long value, int queryAction) {
		if (queryAction != 1 && queryAction != 8 && queryAction != 4) {
			String oper = this.getOperation(queryAction);
			if (!oper.equals("")) {
				this.append(this.att2FName(fieldName) + oper, true);
				this.fieldValues.add(new DefaultCondition(Long.valueOf(value), false, -5));
			}

		}
	}

	protected void where(String fieldName, String value) {
		this.where(fieldName, value, 0);
	}

	protected void where(String fieldName, String value, int queryAction) {
		if (value != null && !value.trim().equals("")) {
			if (queryAction != 8) {
				value = value.trim();
				value = value.toLowerCase();
				String oper = this.getOperation(queryAction);
				if (!oper.equals("")) {
					this.append("lower(" + this.att2FName(fieldName) + ")" + oper, true);
					switch (queryAction) {
						case 1 :
							value = "%" + value + "%";
							break;
						case 101 :
							value = "%" + value;
							break;
						case 102 :
							value = value + "%";
					}

					this.fieldValues.add(new DefaultCondition(value, false, 12));
				} else if (queryAction == 10) {
					this.append(fieldName, true);
					this.fieldValues.add(new DefaultCondition(value, false, 12));
				}

			}
		}
	}

	protected void where(String fieldName, String value, int queryAction, String patterns) {
		if (value != null && !value.trim().equals("")) {
			if (patterns != null && !patterns.trim().equals("")) {
				Date d = DateUtils.valueOf(value, patterns, "");
				if (d != null) {
					this.where(fieldName, d, queryAction);
				}
			} else {
				this.where(fieldName, value, queryAction);
			}

		}
	}

	protected void where(String fieldName, String[] values) {
		if (values != null && values.length >= 1) {
			this.append(this.att2FName(fieldName) + " in (", true);

			for (int i = 0; i < values.length; ++i) {
				if (i == 0) {
					this.append("?", false);
				} else {
					this.append(",?", false);
				}

				this.fieldValues.add(new DefaultCondition(values[i], false, 12));
			}

			this.append(")", false);
		}
	}

	protected void where(String[] fieldNames, String value, int queryAction) {
		if (fieldNames != null && fieldNames.length >= 1 && value != null && !value.trim().equals("")) {
			if (queryAction != 8) {
				String oper = this.getOperation(queryAction);
				if (!oper.equals("")) {
					this.append("(", true);

					for (int i = 0; i < fieldNames.length; ++i) {
						if (i < fieldNames.length - 1) {
							this.append(this.att2FName(fieldNames[i]) + oper + " or ", false);
						} else {
							this.append(this.att2FName(fieldNames[i]) + oper, false);
						}

						this.fieldValues.add(new DefaultCondition(value, false, 12));
					}

					this.append(")", false);
				}

			}
		}
	}

	protected void whereAsDate(String fieldName, String value, int queryAction) {
		if (value != null && !value.equals("")) {
			Date date = DateUtils.valueOf(value, "yyyy-MM-dd", "");
			this.where(fieldName, date, queryAction);
		}
	}

	protected void whereAsInt(String fieldName, String value, int queryAction) {
		if (value != null && !value.equals("")) {
			try {
				int v = Integer.parseInt(value);
				this.where(fieldName, v, queryAction);
			} catch (Exception arg4) {
			}

		}
	}
}