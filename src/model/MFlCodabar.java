package model;

import java.sql.*;
import java.util.*;
//import org.comus.model.*;

public class MFlCodabar extends X_FLC_CodaBar{
	private static final long serialVersionUID = 1L;
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_AccessLog_ID id
	 *	@param trxName transaction
	 */
	public MFlCodabar (Properties ctx, int flc_codabar_ID, String trxName)
	{
		super (ctx, flc_codabar_ID, trxName);
	}	//	MFlcCodabar

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MFlCodabar (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MFlcCodabar

}	//	MFlcCodabar
