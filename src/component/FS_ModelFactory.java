package component;

import java.sql.ResultSet;

import model.MFlCodabar;

import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.Env;


public class FS_ModelFactory implements IModelFactory {

	@Override
	public Class<?> getClass(String tableName) {
		if (MFlCodabar.Table_Name.equals(tableName))
			return MFlCodabar.class;
		return null;

	}

	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		if (MFlCodabar.Table_Name.equals(tableName))
			return new MFlCodabar(Env.getCtx(), Record_ID, trxName);

		
		return null;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		if (MFlCodabar.Table_Name.equals(tableName))
			return new MFlCodabar(Env.getCtx(), rs, trxName);
		return null;
	}

}
