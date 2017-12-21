package domain;

import java.sql.SQLException;

import controller.Controller;
import db.DbException;
import db.InschrijvingenDb;
import db.OpenLesdagDb;
import db.SessieDb;
import db.StudentDb;

// interface/facade voor alle onderliggende klassen van de OpenClass applicatie
public class OpenClassService {
	
	private OpenLesdagDb openlesdagDb;
	private StudentDb studentDb;
	private SimpleMail mail;
	
	public OpenClassService() {
		try {
			openlesdagDb = new OpenLesdagDb();
			studentDb = new StudentDb();
			mail = new SimpleMail(this);
		} catch (ClassNotFoundException e) {
			throw new DomainException(e.getMessage());
		} catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
	
	public OpenLesDag getOpenlesdagVanSessie(int sessieId) {
		try {
			return openlesdagDb.getOpenlesdagVanSessie(sessieId);
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public void sendMail(Student student, OpenClassSession sessie) {
		try {
			mail.sendMail(student, sessie);
		} catch (Exception e) {
			throw new DomainException(e.getMessage());
		}
	}

	public void updateStudent(Student nieuweStudent, int sessionId) {
		studentDb.updateStudent(nieuweStudent);
	}
	
	
	
}
