package com.face.util;

/**
 * 全局静态常量
 * @author Jason Xie 2015-11-6 10:37:52
 *
 */
public class Constants {
	/**
	 * 系统配置
	 * @author Jason Xie 2015-11-16 11:28:33
	 *
	 */
	public static class Sys {
		/**
		 * 文件上传目录
		 */
		public static final String UPLOAD_DIR = "D:\\workspace\\face-cloud\\files\\";
		/**
		 * OCR 账户
		 */
		public static final String OCR_API_USER_NAME = "X403368945";
		/**
		 * OCR 密钥
		 */
		public static final String OCR_API_LICENSE_CODE = "BB1046B2-5A28-47E0-B0FA-03ED8DB29213";
	}
	/**
	 * 公司性质状态定义,1、合伙人，2、有限责任公司，3、股份有限责任公司，4、集团公司，5、上市公司
	 * @author Jason Xie 2015-11-6 10:47:14
	 *
	 */
	public static class OwnershipStatus{
		/**
		 * 合伙人
		 */
		public static final int PARTNER = 1;
		/**
		 * 有限责任公司
		 */
		public static final int LIMITED_LIABILITY_COMPANY = 2;
		/**
		 * 股份有限责任公司 
		 */
		public static final int CORPORATION_LIMITED_COMPANY = 3;
		/**
		 * 集团公司 
		 */
		public static final int GROUP_COMPANY = 4;
		/**
		 * 上市公司 
		 */
		public static final int LISTED_COMPANY = 5;
	} 

	/**
	 * 公司规模状态定义,
	 * @author Jason Xie 2015-11-6 11:06:20
	 *
	 */
	public static class CompanySize{
		/**
		 * JUEST ME
		 */
		public static final int LEAVE_1 = 1;
		/**
		 * 2-6
		 */
		public static final int LEAVE_2 = 2;
		/** 
		 * 7-15
		 */
		public static final int LEAVE_3 = 3;
		/** 
		 * 16-25
		 */
		public static final int LEAVE_4 = 4;
		/**
		 * 26-49
		 */
		public static final int LEAVE_5 = 5;
		/** 
		 * 50-99
		 */
		public static final int LEAVE_6 = 6;
		/** 
		 * 100-249
		 */
		public static final int LEAVE_7 = 7;
		/** 
		 * 250-499
		 */
		public static final int LEAVE_8 = 8;
		/** 
		 * 500-999
		 */
		public static final int LEAVE_9 = 9;
		/** 
		 * 1000-4999
		 */
		public static final int LEAVE_10 = 10;
		/** 
		 * 5000-9999
		 */
		public static final int LEAVE_11 = 11;
		/** 
		 * 10000-29999
		 */
		public static final int LEAVE_12 = 12;
		/** 
		 * 29999+
		 */
		public static final int LEAVE_13 = 13;
	}
	/**
	 * 数据是否有效 （1、有效 ， 0、无效）
	 * @author Jason Xie 2015-11-9 17:50:25
	 *
	 */
	public static class IsValid{
		/**
		 * 有效
		 */
		public static final int YES = 1;
		/**
		 * 无效
		 */
		public static final int NO = 0;
	}
	/**
	 * 类型（1、产品与服务 ，2、需求）
	 * @author Jason Xie 2015-11-12 11:35:36
	 *
	 */
	public static class OfferAndBuy{
		/**
		 * 产品与服务
		 */
		public static final int OFFER = 1;
		/**
		 * 需求
		 */
		public static final int BUY = 2;
	} 	
	/**
	 * 好友状态（1、已发起添加好友邀请， 2、拒绝， 3、同意）
	 * @author Jason Xie 2015-11-12 15:27:45
	 *
	 */
	public static class FriendStatus{
		/**
		 * 已发起添加好友邀请
		 */
		public static final int SEND = 1;
		/**
		 * 拒绝
		 */
		public static final int REJECT = 2;
		/**
		 * 同意
		 */
		public static final int AGREED = 3;
	}
	/**
	 * 票据类型（1、收据， 2、账单， 3、发票）
	 * @author Jason Xie 2015-11-20 15:55:28
	 *
	 */
	public static class InvoiceType{
		/**
		 * 收据
		 */
		public static final int RECEIPT = 1;
		/**
		 * 账单
		 */
		public static final int BILL = 2;
		/**
		 * 发票
		 */
		public static final int INVOICE = 3;
	}
	
	/**
	 * 票据解析状态（1、图片读取中， 2、读取失败， 3、读取完成）
	 * @author Jason Xie 2015-11-20 15:56:20
	 *
	 */
	public static class InvoiceStatus{
		/**
		 * 图片读取中
		 */
		public static final int PARSING = 1;
		/**
		 * 读取失败
		 */
		public static final int FAILURE = 2;
		/**
		 * 读取完成
		 */
		public static final int SUCCESS = 3;
	}
	/**
	 * 产品分类：1、服务， 2、商品
	 * @author Jason Xie 2015-11-25 19:58:14
	 *
	 */
	public static class ProductType{
		/**
		 * 服务
		 */
		public static final int SERVICE = 1;
		/**
		 * 商品
		 */
		public static final int PRODUCT = 2;
	}	
	/**
	 * 产品类型：1、代理销售（进货），0、自产自销
	 * @author Jason Xie 2015-11-25 19:59:05
	 *
	 */
	public static class IsProxy{
		/**
		 * 代理销售（进货）
		 */
		public static final int PROXY = 1;
		/**
		 * 自产自销
		 */
		public static final int SELF = 0;
	}
	/**
	 * 客户分组（1、供应商，2、采购商）
	 * @author Jason Xie 2015-11-25 19:59:05
	 *
	 */
	public static class CustomerGroup{
		/**
		 * 供应商
		 */
		public static final int SUPPLIERS = 1;
		/**
		 * 采购商
		 */
		public static final int BUYER = 2;
	}
	/**
	 * 交易分类（1、应收，2、应付）
	 * @author Jason Xie 2015-11-27 11:53:20
	 *
	 */
	public static class TrunsType{
		/**
		 * 应收
		 */
		public static final int RECEIVABLE = 1;
		/**
		 * 应付
		 */
		public static final int PAYABLE = 2;
	}
	
	
}
