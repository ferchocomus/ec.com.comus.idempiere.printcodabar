package form;

import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;

public class WCodaBar_Production  extends CodaBarMain implements IFormController{

	@Override
	public ADForm getForm() {
		return getADForm();
	}

	public WCodaBar_Production(){
		try {
			zkInit(CodaBarType.PRODUCCION);
			loadValueVariable();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	};
}