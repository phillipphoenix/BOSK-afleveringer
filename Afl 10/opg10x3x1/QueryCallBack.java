package opg10x3x1;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryCallBack {
	public void processRecord(ResultSet rs) throws SQLException;
}
