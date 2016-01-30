import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.CardLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFileChooser;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.*;

import javax.swing.DropMode;

public class ParkingMasterGUI extends JFrame {

	//Panels - basically different menus
	private JPanel contentPane;
	private JPanel panelMainMenu;
	private JPanel panelCustomerMainMenu;
	private JPanel panelAdminMainMenu;
	private JPanel panelReserve;
	private JPanel panelCheckInOutCancel;
	private JPanel panelConfirmation;
	private JPanel panelThankYou;
	private JPanel panelUploadMap;
	private JPanel panelViewReservations;
	private JPanel panelSetParking;
	private JPanel panelSetSections;
	private JPanel panelParkNow;
	//All buttons across all menus
	private JButton btnCustomer;
	private JButton btnAdministrator;
	private JButton btnParkNow;
	private JButton buttonCheckOut;
	private JButton btnMakeReservation;
	private JButton btnReservationCheckIn;
	private JButton btnCancelReservation;
	private JButton btnVMReservations;
	private JButton btnAdminMainMenuBack;
	private JButton btnCOCSubmit;
	private JButton btnCOCBack;
	//All JLabels across all menus
	private JLabel lblRMap;
	private JLabel lblRPrice;
	private JLabel lblPNMap;
	private JLabel labelPNPrice;
	private JLabel lblParkingmaster;
	private JLabel lblCOCLicensePlate;
	private JLabel lblPassword;
	private JLabel labelConfirmationTicketImg;
	
	//All JLabels in the View Reservations Panel
	private JLabel lblVRLicensePlate1;
	private JLabel lblVRPassword1;
	private JLabel lblVRCheckInOut1;
	private JLabel lblVRDate1;
	private JLabel labelVRSpace1;
	private JLabel lblVRLicensePlate2;
	private JLabel lblVRPassword2;
	private JLabel lblVRCheckInOut2;
	private JLabel lblVRDate2;
	private JLabel labelVRSpace2;
	private JLabel lblVRLicensePlate3;
	private JLabel lblVRPassword3;
	private JLabel lblVRCheckInOut3;
	private JLabel lblVRDate3;
	private JLabel labelVRSpace3;
	private JLabel lblVRLicensePlate4;
	private JLabel lblVRPassword4;
	private JLabel lblVRCheckInOut4;
	private JLabel lblVRDate4;
	private JLabel labelVRSpace4;
	private JLabel lblVRLicensePlate5;
	private JLabel lblVRPassword5;
	private JLabel lblVRCheckInOut5;
	private JLabel lblVRDate5;
	private JLabel labelVRSpace5;
	private JLabel lblVRLicensePlate6;
	private JLabel lblVRPassword6;
	private JLabel lblVRCheckInOut6;
	private JLabel lblVRDate6;
	private JLabel labelVRSpace6;
	private JLabel lblVRLicensePlate7;
	private JLabel lblVRPassword7;
	private JLabel lblVRCheckInOut7;
	private JLabel lblVRDate7;
	private JLabel labelVRSpace7;
	private JLabel lblVRPageNum;
	
	
	//All JTextFields across all menus
	private JTextField textFieldPNLicensePlate;
	private JTextField textFieldRLicensePlate;
	private JTextField textFieldCOCLicensePlate;
	private JTextField textFieldSPPrice;
	//All JPassword Fields across all menus
	private JPasswordField textFieldCOCPassword;
	//Used to differentiate between reservation checkin and checkout/cancel reservation
	private boolean reservationCheckIn = false;
	
	//Used to send reservationForm to database
	private ReservationForm tempReservationForm;
	//Used to send parknowForm to database
	private ParkNowForm tempParkNowForm;
	
	//Labels for the confirmation page
	private JLabel lblCTLicensePlate;
	private JLabel lblCTParkingSpot;
	private JLabel lblCTPassword;
	private JLabel lblCTPrice;
	private JLabel lblCTCheckinDate;
	private JLabel lblCTCheckinTime;
	private JLabel lblCTCheckoutDate;
	private JLabel lblCTCheckoutTime;
	
	//Combo Boxes
	private JComboBox comboBoxPNRow;
	private JComboBox comboBoxPNSpace;
	private JComboBox comboBoxPNDuration;
	private JComboBox comboBoxPNLevel;
	private JComboBox comboBoxRRow;
	private JComboBox comboBoxRSpace;
	private JComboBox comboBoxRLevel;
	private JComboBox comboBoxRDuration;
	private JComboBox comboBoxRCheckInTime;
	private JComboBox comboBoxRCheckInDate;
	
	
	private static Database parkingMasterDB;
	private static Frontend frontEnd;
	private ParkNowForm tempPNF;
	private ReservationForm tempRF;
	private JTextField textFieldModifyPrice;
	private JTextField textFieldModifyRows;
	private JTextField textFieldModifySpaces;
	private JTextField textFieldNewPrice;
	private JTextField textFieldNewRows;
	private JTextField textFieldNewSpaces;
	private JTextField textFieldNewLevel;
	
	private Vector<String> VRCurrentPlates = new Vector<String>(7);
	
	
	private int VRpage = 1;
	private int VRmaxPage;
	


	class JTextFieldLimit extends PlainDocument 
	{
	  private int limit;
	  JTextFieldLimit(int limit) {
	    super();
	    this.limit = limit;
	  }

	  JTextFieldLimit(int limit, boolean upper) {
	    super();
	    this.limit = limit;
	  }

	  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	    if (str == null)
	      return;

	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					parkingMasterDB = new Database();
					frontEnd = new Frontend();
					ParkingMasterGUI frame = new ParkingMasterGUI();
					frame.setVisible(true);
					
					//testReservation = new ReservationForm("345-FRD", "xd3jd02d", "A", 2, 3, int timeHours, int timeMinutes, int lengthHours, int lengthMinutes, int dateMonth, int dateDay, int dateYear);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void submitParkNowForm() throws SQLException
	{
		tempPNF = null;
		String tempLevel = comboBoxPNLevel.getSelectedItem().toString();
		
		String tempRow = comboBoxPNRow.getSelectedItem().toString();
		String tempSpace = comboBoxPNSpace.getSelectedItem().toString();
		
		//Getting Duration
		String tempDurationString = comboBoxPNDuration.getSelectedItem().toString();
		int indexOfH = tempDurationString.indexOf('h');
		String hourString = tempDurationString.substring(0, indexOfH);
		int tempHours = Integer.parseInt(hourString);
		int indexOfSpace = tempDurationString.indexOf(' ');
		int indexOfM = tempDurationString.indexOf('m');
		String minuteString = tempDurationString.substring(indexOfSpace + 1, indexOfM);
		int tempMinutes = Integer.parseInt(minuteString);
		int tempDuration = (tempHours * 60);
		tempDuration+= tempMinutes;
		
		Calendar arrivalCal = Calendar.getInstance();
		Calendar checkOutCal = Calendar.getInstance();
		checkOutCal.add(Calendar.MINUTE, tempDuration);
		
		String tempLP = textFieldPNLicensePlate.getText();
		tempLP = verifyLPInput(tempLP);
		
		tempPNF = new ParkNowForm(tempLP, tempLevel,tempRow, tempSpace, tempDuration, arrivalCal, checkOutCal);
		frontEnd.sendForm(tempPNF); 
	}
	
	public void submitReservationForm() throws SQLException
	{
		tempRF = null;
		String tempLevel = comboBoxRLevel.getSelectedItem().toString();
		String tempRow = comboBoxRRow.getSelectedItem().toString();
		String tempSpace = comboBoxRSpace.getSelectedItem().toString();
		
		//Getting Duration
		String tempDurationString = comboBoxRDuration.getSelectedItem().toString();
		int indexOfH = tempDurationString.indexOf('h');
		String hourString = tempDurationString.substring(0, indexOfH);
		int tempHours = Integer.parseInt(hourString);
		int indexOfSpace = tempDurationString.indexOf(' ');
		int indexOfM = tempDurationString.indexOf('m');
		String minuteString = tempDurationString.substring(indexOfSpace + 1, indexOfM);
		int tempMinutes = Integer.parseInt(minuteString);
		int tempDuration = (tempHours * 60);
		tempDuration+= tempMinutes;
		
		//Get checkInTime
		String tempCheckInTime = comboBoxRCheckInTime.getSelectedItem().toString();
		int indexOfFirstSemiColon = tempCheckInTime.indexOf(':');
		String checkInHourString = tempCheckInTime.substring(0, indexOfFirstSemiColon);
		int checkInHour = Integer.parseInt(checkInHourString);
		String checkInMinuteString = tempCheckInTime.substring(indexOfFirstSemiColon+1, indexOfFirstSemiColon+3);
		int checkInMinutes = Integer.parseInt(checkInMinuteString);
		String AM_PM = tempCheckInTime.substring(tempCheckInTime.length()-2);
		Calendar checkInCal = Calendar.getInstance();
		
		//Get checkInDate
		String tempCheckInDate = comboBoxRCheckInDate.getSelectedItem().toString();
		String tempCheckInMonth = tempCheckInDate.substring(0, 2);
		String tempCheckInDay = tempCheckInDate.substring(3, 5);
		String tempCheckInYear = tempCheckInDate.substring(6, 10);
		checkInCal.set(Integer.parseInt(tempCheckInYear), Integer.parseInt(tempCheckInMonth)-1, Integer.parseInt(tempCheckInDay));
		
		//SetCheckInTime
		checkInCal.set(Calendar.HOUR, checkInHour); checkInCal.set(Calendar.MINUTE, checkInMinutes); checkInCal.set(Calendar.AM_PM, (AM_PM.equalsIgnoreCase("AM") ? 0:1 ));
	
		/**checkInCal.set(Calendar.DATE, Integer.parseInt(tempCheckInDay));
		checkInCal.set(Calendar.MONTH, (Integer.parseInt(tempCheckInMonth)));
		checkInCal.set(Calendar.YEAR, Integer.parseInt(tempCheckInYear));**/
		
		
		//Setting checkOutCal and arrival cal
		Calendar checkOutCal = (Calendar) checkInCal.clone();
		checkOutCal.set(Integer.parseInt(tempCheckInYear), Integer.parseInt(tempCheckInMonth)-1, Integer.parseInt(tempCheckInDay));
		checkOutCal.add(Calendar.MINUTE, tempDuration);
		
		Calendar arrivalCal = Calendar.getInstance();
	
		String tempLP = textFieldRLicensePlate.getText();
		tempLP = verifyLPInput(tempLP);
		
		tempRF = new ReservationForm(tempLP, tempLevel,tempRow, tempSpace, tempDuration, arrivalCal, checkOutCal, checkInCal);
		frontEnd.sendForm(tempRF); 
	}
	//Int: 0 correlates with ParkNowForm, 1 correlates with ReservationForm
	public void populateConfirmationInfo(int type) throws SQLException
	{
		//Printing confirmation page
		if(type == 0)
		{
			lblCTLicensePlate.setText("License Plate: " + tempPNF.getPlate());
			lblCTParkingSpot.setText("Parking Space: " + tempPNF.getLevel()+"-"+tempPNF.getRow()+"-"+tempPNF.getSpace());
			lblCTPassword.setText("Password: " + tempPNF.getPassword());
			lblCTPrice.setText("Price: $" + String.format("%.2f", (frontEnd.getParkingSpacePrice(tempPNF.getLevel(), tempPNF.getRow(), tempPNF.getSpace())*tempPNF.getDuration()/60)));
			lblCTCheckinDate.setText("Check-In Date: " +tempPNF.getArrivalDateString());
			lblCTCheckinTime.setText("Check-In Time: " + tempPNF.getArrivalTimeString());
			lblCTCheckoutDate.setText("Check-Out Date: "+ tempPNF.getCheckOutDateString());
	   	    lblCTCheckoutTime.setText("Check-Out Time: "+ tempPNF.getCheckOutTimeString());
		}
		//Printing confirmation page
		else if(type == 1)
		{
			lblCTLicensePlate.setText("License Plate: " + tempRF.getPlate());
			lblCTParkingSpot.setText("Parking Space: " + tempRF.getLevel()+"-"+tempRF.getRow()+"-"+tempRF.getSpace());
			lblCTPassword.setText("Password: " + tempRF.getPassword());
			lblCTPrice.setText("Price: $" + String.format("%.2f", (frontEnd.getParkingSpacePrice(tempRF.getLevel(), tempRF.getRow(), tempRF.getSpace())*tempRF.getDuration()/60)));
			lblCTCheckinDate.setText("Check-In Date: " +tempRF.getCheckInDateString());
			lblCTCheckinTime.setText("Check-In Time: " + tempRF.getCheckInTimeString());
			lblCTCheckoutDate.setText("Check-Out Date: "+ tempRF.getCheckOutDateString());
	   	    lblCTCheckoutTime.setText("Check-Out Time: "+ tempRF.getCheckOutTimeString());
		}
	}
	
	/**
	 * 
	 * @param mapLevelName - this is grabbed from the combo box to specify which map should be displayed
	 * @param form - Either pass in PN or R to specify which panel to update
	 */
	private void updateMapImage(String mapLevelName, String form)
	{
		Image img = new ImageIcon(this.getClass().getResource("/Level"+mapLevelName.toUpperCase()+".jpg")).getImage();
		if(form.equalsIgnoreCase("PN"))
			lblPNMap.setIcon(new ImageIcon(img));
		else
			lblRMap.setIcon(new ImageIcon(img));
	}
	/**
	 * 
	 * @param level
	 * @param row
	 * @param space
	 * @param panel - Is it for parkNow or Reserve? (expecting PN or R)
	 * @return
	 * @throws SQLException
	 */
	private void updateParkingPrice(String level, String row, String space, String panel) throws SQLException
	{
		if(row==null||space==null)
		{
			return;
		}
			double price = frontEnd.getParkingSpacePrice(level, row, space);
		//Getting Duration
		String tempDurationString = "";
		if(panel.equalsIgnoreCase("PN"))
			tempDurationString = comboBoxPNDuration.getSelectedItem().toString();
		else
			tempDurationString = comboBoxRDuration.getSelectedItem().toString();
		int indexOfH = tempDurationString.indexOf('h');
		String hourString = tempDurationString.substring(0, indexOfH);
		int tempHours = Integer.parseInt(hourString);
		int indexOfSpace = tempDurationString.indexOf(' ');
		int indexOfM = tempDurationString.indexOf('m');
		String minuteString = tempDurationString.substring(indexOfSpace + 1, indexOfM);
		int tempMinutes = Integer.parseInt(minuteString);
		double tempDuration = (tempHours * 60);
		tempDuration+= tempMinutes;
		tempDuration = tempDuration/60;
		
		//Format Price to be 4 digits
		
		if(panel.equalsIgnoreCase("PN"))
		{
			labelPNPrice.setText("Price: $" + String.format("%.2f", price*tempDuration));
		}
		else if(panel.equalsIgnoreCase("R"))
		{
			lblRPrice.setText("Price: $" + String.format("%.2f", price*tempDuration));
		}
		
	}
	
	private void populateViewReservations() throws NumberFormatException, SQLException
	{
		Vector<ReservationForm> reservations = frontEnd.getReservations();
		
		VRCurrentPlates.ensureCapacity(7);
		for(int i = 0; i < 7; i++)
			VRCurrentPlates.add("");
		
		VRmaxPage = (reservations.size() / 7);
		if(reservations.size() % 7 > 0)
			VRmaxPage++;
		
		lblVRPageNum.setText("Page " + VRpage + "/" + VRmaxPage);
		
		//System.out.println("Reservations: " + reservations.size());
		//System.out.println("Page " + VRpage + "/" + VRmaxPage);
		
		JLabel VRLabels[][] = new JLabel[][]{
				{lblVRLicensePlate1, lblVRPassword1, lblVRCheckInOut1, lblVRDate1, labelVRSpace1},
				{lblVRLicensePlate2, lblVRPassword2, lblVRCheckInOut2, lblVRDate2, labelVRSpace2},
				{lblVRLicensePlate3, lblVRPassword3, lblVRCheckInOut3, lblVRDate3, labelVRSpace3},
				{lblVRLicensePlate4, lblVRPassword4, lblVRCheckInOut4, lblVRDate4, labelVRSpace4},
				{lblVRLicensePlate5, lblVRPassword5, lblVRCheckInOut5, lblVRDate5, labelVRSpace5},
				{lblVRLicensePlate6, lblVRPassword6, lblVRCheckInOut6, lblVRDate6, labelVRSpace6},
				{lblVRLicensePlate7, lblVRPassword7, lblVRCheckInOut7, lblVRDate7, labelVRSpace7}
		};
		for(int row = 0; row < 7;row++)
		{
			for(int col = 0; col<5; col++)
			{
				int currentRes = ((VRpage - 1) * 7) + row;
				
				if(col == 0)
				{
					VRLabels[row][col].setText(
							(currentRes < reservations.size()) ?
							"<HTML><b>License Plate:</b><br/>" + reservations.get(currentRes).getPlate() + "</HTML>" :
							"<HTML><b>License Plate:</b><br/>N/A</HTML>"
							);
					VRCurrentPlates.set(row, (currentRes < reservations.size()) ? reservations.get(currentRes).getPlate() : "");
				}
				if(col == 1)
					VRLabels[row][col].setText(
							(currentRes < reservations.size()) ?
							"<HTML><b>Password:</b><br/>" + reservations.get(currentRes).getPassword() + "</HTML>" :
							"<HTML><b>License Plate:</b><br/>N/A</HTML>"
							);
				if(col == 2)
					VRLabels[row][col].setText(
							(currentRes < reservations.size()) ?
							"<HTML><b>Check in Time:</b><br/>" + reservations.get(currentRes).getCheckInTimeString() + "</HTML>" :
							"<HTML><b>Check in Time:</b><br/>N/A</HTML>"
							);
				if(col == 3)
					VRLabels[row][col].setText(
							(currentRes < reservations.size()) ?
							"<HTML><b>Check in Date:</b><br/>" + reservations.get(currentRes).getCheckInDateString() + "</HTML>" :
							"<HTML><b>Check in Date:</b><br/>N/A</HTML>"
							);
				if(col == 4)
					VRLabels[row][col].setText(
							(currentRes < reservations.size()) ?
							"<HTML><b>Space:</b><br/>" + reservations.get(currentRes).getLevel()+"-"+ reservations.get(currentRes).getRow()+"-"+ reservations.get(currentRes).getSpace() + "</HTML>" :
							"<HTML><b>Space:</b><br/>N/A</HTML>"
							);
			}
		}
	}
	private String verifyLPInput(String licensePlate)
	{
		StringBuilder sb = new StringBuilder(licensePlate);
		for(int i = 0; i<sb.length();i++)
		{
			char c = sb.charAt(i);
			if(c == '-')
			{
				sb.deleteCharAt(i);
			}
		}
		return sb.toString();
	}
	
	private void clearPanelParkNow()
	{
		comboBoxPNLevel.setSelectedIndex(0);
		comboBoxPNRow.setSelectedIndex(0);
		comboBoxPNSpace.setSelectedIndex(0);
		comboBoxPNDuration.setSelectedIndex(0);
		textFieldPNLicensePlate.setText("");
		
	}
	
	private void clearPanelReserve()
	{
		comboBoxRLevel.setSelectedIndex(0);
		comboBoxRRow.setSelectedIndex(0);
		comboBoxRSpace.setSelectedIndex(0);
		comboBoxRCheckInTime.setSelectedIndex(0);
		comboBoxRDuration.setSelectedIndex(0);
		comboBoxRCheckInDate.setSelectedIndex(0);
		textFieldRLicensePlate.setText("");
	}
	
	private void clearPanelCheckInOutCancel()
	{
		textFieldCOCLicensePlate.setText("");
		textFieldCOCPassword.setText("");
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ParkingMasterGUI() throws SQLException 
	{
		setBackground(SystemColor.textHighlight);
		setTitle("ParkingMasterv1.0");
		Image icon = new ImageIcon(this.getClass().getResource("/carlogo.png")).getImage();
		setIconImage(icon);
		setFont(new Font("Candara", Font.PLAIN, 17));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.textHighlight);
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		panelMainMenu = new JPanel();
		panelMainMenu.setBackground(SystemColor.textHighlight);
		contentPane.add(panelMainMenu, "name_385939876031985");
		panelMainMenu.setLayout(null);
		
		btnCustomer = new JButton("Customer");
		btnCustomer.setFont(new Font("Candara", Font.PLAIN, 17));
		//GO TO CUSTOMER MENU AFTER USER CLICKS ON 'CUSTOMER'
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				panelCustomerMainMenu.setVisible(true);
				panelMainMenu.setVisible(false);
			}
		});
		btnCustomer.setBounds(95, 163, 224, 135);
		panelMainMenu.add(btnCustomer);
		
		btnAdministrator = new JButton("Administrator");
		btnAdministrator.setFont(new Font("Candara", Font.PLAIN, 17));
		//GO TO ADMINISTRATOR MENU AFTER USER CLICKS ON 'ADMINISTRATOR'
		btnAdministrator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelAdminMainMenu.setVisible(true);
				panelMainMenu.setVisible(false);
			}
		});
		btnAdministrator.setBounds(414, 163, 224, 135);
		panelMainMenu.add(btnAdministrator);
		
		lblParkingmaster = new JLabel("ParkingMaster v1.0");
		lblParkingmaster.setHorizontalAlignment(SwingConstants.CENTER);
		lblParkingmaster.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 33));
		lblParkingmaster.setBounds(10, 11, 714, 49);
		panelMainMenu.add(lblParkingmaster);
		
		panelCustomerMainMenu = new JPanel();
		panelCustomerMainMenu.setBackground(SystemColor.textHighlight);
		contentPane.add(panelCustomerMainMenu, "name_385949571638603");
		panelCustomerMainMenu.setLayout(null);
		
		btnParkNow = new JButton("Park Now");
		//GO TO PARK NOW/RESERVE MENU AFTER USER CLICKS ON 'PARK NOW'
		btnParkNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				panelCustomerMainMenu.setVisible(false);
				panelParkNow.setVisible(true);
			}
		});
		btnParkNow.setFont(new Font("Candara", Font.PLAIN, 17));
		btnParkNow.setBounds(43, 71, 187, 190);
		panelCustomerMainMenu.add(btnParkNow);
		
		buttonCheckOut = new JButton("Check Out");
		//GO TO CHECK IN/OUT/CANCEL RESERVATION MENU AFTER USER CLICKS ON 'CHECK OUT'
		buttonCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelCheckInOutCancel.setVisible(true);
				panelCustomerMainMenu.setVisible(false);
			}
		});
		buttonCheckOut.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonCheckOut.setBounds(503, 71, 187, 190);
		panelCustomerMainMenu.add(buttonCheckOut);
		
		btnMakeReservation = new JButton("Make Reservation");
		//GO TO PARK NOW/RESERVE MENU AFTER CLICKING ON 'MAKE RESERVATION'
		btnMakeReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelCustomerMainMenu.setVisible(false);
				panelReserve.setVisible(true);
			}
		});
		btnMakeReservation.setFont(new Font("Candara", Font.PLAIN, 17));
		btnMakeReservation.setBounds(95, 360, 224, 59);
		panelCustomerMainMenu.add(btnMakeReservation);
		
		btnReservationCheckIn = new JButton("Reservation Check In");
		//GO TO CHECK IN/OUT/CANCEL RESERVATION MENU AFTER USER CLICKS ON 'CHECK IN'
		btnReservationCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//Setting boolean to true in order to access confirmation screen at end of transaction
				reservationCheckIn = true;
				panelCheckInOutCancel.setVisible(true);
				panelCustomerMainMenu.setVisible(false);
			}
		});
		btnReservationCheckIn.setFont(new Font("Candara", Font.PLAIN, 17));
		btnReservationCheckIn.setBounds(273, 71, 187, 190);
		panelCustomerMainMenu.add(btnReservationCheckIn);
		
		btnCancelReservation = new JButton("Cancel Reservation");
		//GO TO CHECK IN/OUT/CANCEL RESERVATION MENU AFTER USER CLICKS ON 'CANCEL RESERVATION'
		btnCancelReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelCheckInOutCancel.setVisible(true);
				panelCustomerMainMenu.setVisible(false);
			}
		});
		btnCancelReservation.setFont(new Font("Candara", Font.PLAIN, 17));
		btnCancelReservation.setBounds(414, 360, 224, 59);
		panelCustomerMainMenu.add(btnCancelReservation);
		
		JButton btnCustomerMainMenuBack = new JButton("Back");
		btnCustomerMainMenuBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				panelCustomerMainMenu.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnCustomerMainMenuBack.setFont(new Font("Candara", Font.PLAIN, 17));
		btnCustomerMainMenuBack.setBounds(10, 10, 70, 25);
		panelCustomerMainMenu.add(btnCustomerMainMenuBack);
		
		panelAdminMainMenu = new JPanel();
		panelAdminMainMenu.setBackground(SystemColor.textHighlight);
		contentPane.add(panelAdminMainMenu, "name_385876703751927");
		panelAdminMainMenu.setLayout(null);
		
		btnVMReservations = new JButton("View/Modify Reservations");
		//Go to view reservations from admin main menu
		btnVMReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelViewReservations.setVisible(true);
				
				panelAdminMainMenu.setVisible(false);
				//Try populating the View Reservations 
				VRpage = 1;
				try {
					populateViewReservations();
				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVMReservations.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVMReservations.setBounds(225, 168, 284, 114);
		panelAdminMainMenu.add(btnVMReservations);
		
		btnAdminMainMenuBack = new JButton("Back");
		btnAdminMainMenuBack.setFont(new Font("Candara", Font.PLAIN, 17));
		//GO TO MAIN MENU AFTER USER CLICKS ON 'BACK'
		btnAdminMainMenuBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelAdminMainMenu.setVisible(false);
				panelMainMenu.setVisible(true);
			}
		});
		btnAdminMainMenuBack.setBounds(10, 10, 70, 25);
		panelAdminMainMenu.add(btnAdminMainMenuBack);
		
		panelReserve = new JPanel();
		contentPane.add(panelReserve, "name_56407527115297");
		panelReserve.setLayout(null);
		
		comboBoxRLevel = new JComboBox(frontEnd.getLevels());
		comboBoxRLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox cb = (JComboBox)e.getSource();
				String mapLevel = (String)cb.getSelectedItem();
				String mapRow = (String)comboBoxRRow.getSelectedItem();
				String mapSpace = (String)comboBoxRSpace.getSelectedItem();
				//Update Map Image
				updateMapImage(mapLevel, "R");
				
				//Update spaces and rows based on what level is selected
				try {
					comboBoxRRow.setModel(frontEnd.getRows(mapLevel));
					comboBoxRSpace.setModel(frontEnd.getSpaces(mapLevel, mapRow));
					updateParkingPrice(mapLevel,mapRow,mapSpace,"R");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		comboBoxRLevel.setBounds(20, 72, 100, 25);
		panelReserve.add(comboBoxRLevel);
		
		JButton btnRBack = new JButton("Back");
		//GO TO CUSTOMER MENU AFTER USER CLICKS ON 'BACK'
		btnRBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				clearPanelReserve();
				panelReserve.setVisible(false);
				panelCustomerMainMenu.setVisible(true);
			}
		});
		btnRBack.setFont(new Font("Candara", Font.PLAIN, 17));
		btnRBack.setBounds(10, 10, 70, 25);
		panelReserve.add(btnRBack);
		
		//Setting up map for display
		lblRMap = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/LevelA.jpg")).getImage();
		lblRMap.setIcon(new ImageIcon(img));
		Border mapBorder = LineBorder.createBlackLineBorder();
		lblRMap.setBorder(mapBorder);
		lblRMap.setBounds(281, 56, 445, 300);
		panelReserve.add(lblRMap);
		
		comboBoxRRow = new JComboBox(frontEnd.getRows("A"));
		comboBoxRRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				JComboBox cb = (JComboBox)arg0.getSource();
				String mapRow = (String)cb.getSelectedItem();
				String mapLevel = (String)comboBoxRLevel.getSelectedItem();
				String mapSpace = (String)comboBoxRSpace.getSelectedItem();
				try {
					comboBoxRSpace.setModel(frontEnd.getSpaces(mapLevel, mapRow));
					updateParkingPrice(mapLevel, mapRow,mapSpace,"R");
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		comboBoxRRow.setBounds(160, 72, 100, 25);
		panelReserve.add(comboBoxRRow);
		
		JLabel lblRLevel = new JLabel("Level");
		lblRLevel.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRLevel.setBounds(20, 59, 46, 14);
		panelReserve.add(lblRLevel);
		
		JLabel lblRRow = new JLabel("Row");
		lblRRow.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRRow.setBounds(160, 59, 46, 14);
		panelReserve.add(lblRRow);
		
		comboBoxRSpace = new JComboBox(frontEnd.getSpaces("A", "1"));
		comboBoxRSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox cb = (JComboBox)e.getSource();
				String mapSpace = (String)cb.getSelectedItem();
				String mapRow = (String)comboBoxRRow.getSelectedItem();
				String mapLevel = (String)comboBoxRLevel.getSelectedItem();
				try {
					updateParkingPrice(mapLevel, mapRow,mapSpace,"R");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBoxRSpace.setBounds(20, 138, 100, 25);
		panelReserve.add(comboBoxRSpace);
		
		JLabel lblRSpace = new JLabel("Space");
		lblRSpace.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRSpace.setBounds(20, 123, 46, 14);
		panelReserve.add(lblRSpace);
		
		comboBoxRCheckInTime = new JComboBox(frontEnd.getTimes());
		comboBoxRCheckInTime.setBounds(160, 138, 100, 25);
		panelReserve.add(comboBoxRCheckInTime);
		
		JLabel lblRCheckInTime = new JLabel("CheckInTime");
		lblRCheckInTime.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRCheckInTime.setBounds(160, 119, 93, 22);
		panelReserve.add(lblRCheckInTime);
		
		comboBoxRDuration = new JComboBox(frontEnd.getDurations());
		comboBoxRDuration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String mapSpace = (String)comboBoxRSpace.getSelectedItem();
				String mapRow = (String)comboBoxRRow.getSelectedItem();
				String mapLevel = (String)comboBoxRLevel.getSelectedItem();
				try {
					updateParkingPrice(mapLevel, mapRow,mapSpace,"R");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBoxRDuration.setBounds(20, 202, 100, 25);
		panelReserve.add(comboBoxRDuration);
		
		JLabel lblRDuration = new JLabel("Duration");
		lblRDuration.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRDuration.setBounds(20, 184, 63, 22);
		panelReserve.add(lblRDuration);
		
		comboBoxRCheckInDate = new JComboBox(frontEnd.getDates());
		comboBoxRCheckInDate.setBounds(160, 202, 100, 25);
		panelReserve.add(comboBoxRCheckInDate);
		
		JLabel lblRCheckInDate = new JLabel("Check In Date");
		lblRCheckInDate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRCheckInDate.setBounds(160, 184, 99, 22);
		panelReserve.add(lblRCheckInDate);
		
		textFieldRLicensePlate = new JTextField();
		textFieldRLicensePlate.setBounds(20, 280, 100, 25);
		panelReserve.add(textFieldRLicensePlate);
		textFieldRLicensePlate.setColumns(10);
		textFieldRLicensePlate.setDocument(new JTextFieldLimit(9));
		
		JLabel lblRLicensePlate = new JLabel("License Plate");
		lblRLicensePlate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRLicensePlate.setBounds(20, 267, 115, 14);
		panelReserve.add(lblRLicensePlate);
		
		JButton btnRSubmit = new JButton("Submit");
		btnRSubmit.setForeground(Color.BLACK);
		btnRSubmit.setBackground(Color.GREEN);
		btnRSubmit.setOpaque(true);
		
		btnRSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(textFieldRLicensePlate.getText().equals(""))
				{
					JOptionPane.showMessageDialog(panelReserve, "License plate field is empty.");
				}
				else
				{
					try 
					{
						//Send the Reservation Form to the DB
						submitReservationForm();
						//Populates the ticket (1 means it is a reservation form)
						populateConfirmationInfo(1);
						//Clears the reserve panel of past user info
						clearPanelReserve();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					panelReserve.setVisible(false);
					panelConfirmation.setVisible(true);
				}
			}
		});
		btnRSubmit.setFont(new Font("Candara", Font.PLAIN, 17));
		btnRSubmit.setBounds(20, 333, 89, 23);
		panelReserve.add(btnRSubmit);
		
		lblRPrice = new JLabel();
		if(comboBoxRLevel.getItemCount()==0||comboBoxRRow.getItemCount()==0||comboBoxRSpace.getItemCount()==0)
		{
			lblRPrice.setText("0.00");
		}
		else
		{
			updateParkingPrice(comboBoxRLevel.getItemAt(0).toString(), comboBoxRRow.getItemAt(0).toString(), comboBoxRSpace.getItemAt(0).toString(), "R");
		}
		
		lblRPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		lblRPrice.setBounds(160, 280, 111, 25);
		panelReserve.add(lblRPrice);
		
		panelCheckInOutCancel = new JPanel();
		contentPane.add(panelCheckInOutCancel, "name_56415662059494");
		panelCheckInOutCancel.setLayout(null);
		
		textFieldCOCLicensePlate = new JTextField();
		textFieldCOCLicensePlate.setBounds(215, 186, 100, 25);
		panelCheckInOutCancel.add(textFieldCOCLicensePlate);
		textFieldCOCLicensePlate.setColumns(10);
		textFieldCOCLicensePlate.setDocument(new JTextFieldLimit(9));
		
		lblCOCLicensePlate = new JLabel("License Plate");
		lblCOCLicensePlate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCOCLicensePlate.setBounds(215, 173, 100, 14);
		panelCheckInOutCancel.add(lblCOCLicensePlate);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Candara", Font.PLAIN, 17));
		lblPassword.setBounds(410, 173, 100, 14);
		panelCheckInOutCancel.add(lblPassword);
		
		textFieldCOCPassword = new JPasswordField();
		textFieldCOCPassword.setBounds(410, 186, 100, 25);
		panelCheckInOutCancel.add(textFieldCOCPassword);
		
		btnCOCSubmit = new JButton("Submit");
		btnCOCSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String licensePlate = textFieldCOCLicensePlate.getText();
				licensePlate = verifyLPInput(licensePlate);
				char passwordArray[] = textFieldCOCPassword.getPassword();
				String password = new String(passwordArray);
				if(!frontEnd.doesReservationExist(licensePlate, password))
					JOptionPane.showMessageDialog(panelCheckInOutCancel, "Reservation does not exist.");
				else
				{
					try {
						if(!frontEnd.verifyUserPassword(licensePlate, password))
						{
							JOptionPane.showMessageDialog(panelCheckInOutCancel, "Password Incorrect");
							textFieldCOCLicensePlate.setText("");
							textFieldCOCPassword.setText("");
							return;
						}
						else
						{
							
							//If it is a reservation check in, we show a confirmation ticket, else we show a thank you screen for checkouts/cancel reservationers
							if(reservationCheckIn == true)
							{
								try 
								{
									if(frontEnd.isReservationCheckedIn(licensePlate))
										JOptionPane.showMessageDialog(panelCheckInOutCancel, "The entered reservation is already checked in.");
									else if(!frontEnd.validCheckInTime(licensePlate))
										JOptionPane.showMessageDialog(panelCheckInOutCancel, "You are early! Unsuccessful check In");
									else
									{
										frontEnd.reservationCheckIn(licensePlate);
										panelThankYou.setVisible(true);
										panelCheckInOutCancel.setVisible(false);
									}
								} 
								catch (SQLException e1) 
								{
									e1.printStackTrace();
								}
								//panelConfirmation.setVisible(true);
								//Reseting boolean to false so the program flow doesn't come through here without reservation check in
								reservationCheckIn = false;
							}
							else
							{
								try 
								{
									//Display fee to user
									double fee = frontEnd.checkForFeeDue(licensePlate);
									double feeRate = 0.15;
									if(fee>0)
									{
										JOptionPane.showMessageDialog(panelCheckInOutCancel, "You checked out "+(int)(fee/feeRate)+" minutes late. Please pay the fee of: $"+fee);
									}
									frontEnd.deleteUser(licensePlate);
								} 
								catch (SQLException e1) 
								{
									e1.printStackTrace();
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								panelThankYou.setVisible(true);
								panelCheckInOutCancel.setVisible(false);
							}
						}
					} catch (HeadlessException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				clearPanelCheckInOutCancel();
			}
		});
		btnCOCSubmit.setFont(new Font("Candara", Font.PLAIN, 17));
		btnCOCSubmit.setBounds(277, 240, 180, 52);
		panelCheckInOutCancel.add(btnCOCSubmit);
		
		btnCOCBack = new JButton("Back");
		btnCOCBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelCheckInOutCancel.setVisible(false);
				panelCustomerMainMenu.setVisible(true);
			}
		});
		btnCOCBack.setFont(new Font("Candara", Font.PLAIN, 17));
		btnCOCBack.setBounds(10, 10, 70, 25);
		panelCheckInOutCancel.add(btnCOCBack);
		
		panelConfirmation = new JPanel();
		contentPane.add(panelConfirmation, "name_56421838438094");
		panelConfirmation.setLayout(null);
		Image confTicket = new ImageIcon(this.getClass().getResource("/confirmationTicket.png")).getImage();
		
		JButton btnCTDone = new JButton("Done");
		//Returns user to main screen
		btnCTDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelConfirmation.setVisible(false);
				panelCustomerMainMenu.setVisible(true);
			}
		});
		btnCTDone.setFont(new Font("Candara", Font.PLAIN, 17));
		btnCTDone.setBounds(311, 359, 115, 29);
		panelConfirmation.add(btnCTDone);
		
		lblCTLicensePlate = new JLabel("License Plate: ");
		lblCTLicensePlate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTLicensePlate.setBounds(123, 93, 214, 22);
		panelConfirmation.add(lblCTLicensePlate);
		
		JLabel lblCTTitle = new JLabel("PARKING MASTER TICKET CONFIRMATION");
		lblCTTitle.setFont(new Font("Candara", Font.PLAIN, 25));
		lblCTTitle.setBounds(123, 55, 451, 31);
		panelConfirmation.add(lblCTTitle);
		
		lblCTPassword = new JLabel("Password: ");
		lblCTPassword.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTPassword.setBounds(123, 142, 214, 22);
		panelConfirmation.add(lblCTPassword);
		
		lblCTParkingSpot = new JLabel("Parking Spot: ");
		lblCTParkingSpot.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTParkingSpot.setBounds(360, 93, 214, 22);
		panelConfirmation.add(lblCTParkingSpot);
		
		lblCTCheckinTime = new JLabel("Check-In Time:");
		lblCTCheckinTime.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTCheckinTime.setBounds(360, 189, 214, 22);
		panelConfirmation.add(lblCTCheckinTime);
		
		lblCTCheckoutTime = new JLabel("Check-Out Time:");
		lblCTCheckoutTime.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTCheckoutTime.setBounds(360, 229, 214, 22);
		panelConfirmation.add(lblCTCheckoutTime);
		
		lblCTPrice = new JLabel("Price:");
		lblCTPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTPrice.setBounds(360, 142, 214, 22);
		panelConfirmation.add(lblCTPrice);
		
		lblCTCheckinDate = new JLabel("Check-In Date:");
		lblCTCheckinDate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTCheckinDate.setBounds(123, 189, 214, 22);
		panelConfirmation.add(lblCTCheckinDate);
		
		lblCTCheckoutDate = new JLabel("Check-Out Date:");
		lblCTCheckoutDate.setFont(new Font("Candara", Font.PLAIN, 17));
		lblCTCheckoutDate.setBounds(123, 229, 214, 22);
		panelConfirmation.add(lblCTCheckoutDate);
		
		labelConfirmationTicketImg = new JLabel("");
		labelConfirmationTicketImg.setIcon(new ImageIcon(confTicket));
		labelConfirmationTicketImg.setBounds(54, 16, 625, 300);
		panelConfirmation.add(labelConfirmationTicketImg);
		
		panelThankYou = new JPanel();
		contentPane.add(panelThankYou, "name_56425822334372");
		panelThankYou.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Thank You!");
		lblNewLabel.setFont(new Font("Candara", Font.PLAIN, 33));
		lblNewLabel.setBounds(286, 185, 162, 91);
		panelThankYou.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelThankYou.setVisible(false);
				panelCustomerMainMenu.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Candara", Font.PLAIN, 17));
		btnNewButton.setBounds(322, 322, 89, 23);
		panelThankYou.add(btnNewButton);
		
		panelUploadMap = new JPanel();
		contentPane.add(panelUploadMap, "name_56453434404081");
		panelUploadMap.setLayout(null);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		fileChooser.setBounds(61, 43, 611, 328);
		panelUploadMap.add(fileChooser);
		
		JButton buttonUMBack = new JButton("Back");
		//Back to admin main menu from upload map
		buttonUMBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelUploadMap.setVisible(false);
				panelAdminMainMenu.setVisible(true);
			}
		});
		buttonUMBack.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonUMBack.setBounds(10, 10, 70, 25);
		panelUploadMap.add(buttonUMBack);
		
		panelViewReservations = new JPanel();
		contentPane.add(panelViewReservations, "name_56475225105090");
		panelViewReservations.setLayout(null);
		
		JButton buttonVRBack = new JButton("Back");
		//Back to admin main menu from View Reservations
		buttonVRBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelViewReservations.setVisible(false);
				panelAdminMainMenu.setVisible(true);
			}
		});
		buttonVRBack.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonVRBack.setBounds(10, 10, 70, 25);
		panelViewReservations.add(buttonVRBack);
		
		lblVRLicensePlate1 = new JLabel("License Plate: ");
		lblVRLicensePlate1.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate1.setBounds(10, 42, 135, 52);
		panelViewReservations.add(lblVRLicensePlate1);
		
		lblVRPassword1 = new JLabel("Password: ");
		lblVRPassword1.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword1.setBounds(147, 42, 135, 52);
		panelViewReservations.add(lblVRPassword1);
		
		lblVRCheckInOut1 = new JLabel("Check In/Out: ");
		lblVRCheckInOut1.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut1.setBounds(286, 42, 135, 52);
		panelViewReservations.add(lblVRCheckInOut1);
		
		lblVRDate1 = new JLabel("Date: ");
		lblVRDate1.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate1.setBounds(425, 42, 110, 52);
		panelViewReservations.add(lblVRDate1);
		
		labelVRSpace1 = new JLabel("Space: ");
		labelVRSpace1.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace1.setBounds(535, 42, 100, 52);
		panelViewReservations.add(labelVRSpace1);
		
		JButton btnVRDelete1 = new JButton("Delete");
		btnVRDelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(0) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(0));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete1.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete1.setBounds(635, 54, 89, 23);
		panelViewReservations.add(btnVRDelete1);
		
		lblVRLicensePlate2 = new JLabel("License Plate: ");
		lblVRLicensePlate2.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate2.setBounds(10, 90, 135, 52);
		panelViewReservations.add(lblVRLicensePlate2);
		
		lblVRPassword2 = new JLabel("Password: ");
		lblVRPassword2.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword2.setBounds(147, 90, 135, 52);
		panelViewReservations.add(lblVRPassword2);
		
		lblVRCheckInOut2 = new JLabel("Check In/Out: ");
		lblVRCheckInOut2.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut2.setBounds(286, 90, 135, 52);
		panelViewReservations.add(lblVRCheckInOut2);
		
		lblVRDate2 = new JLabel("Date: ");
		lblVRDate2.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate2.setBounds(425, 90, 110, 52);
		panelViewReservations.add(lblVRDate2);
		
		labelVRSpace2 = new JLabel("Space: ");
		labelVRSpace2.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace2.setBounds(535, 90, 100, 52);
		panelViewReservations.add(labelVRSpace2);
		
		JButton btnVRDelete2 = new JButton("Delete");
		btnVRDelete2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(1) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(1));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete2.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete2.setBounds(635, 102, 89, 23);
		panelViewReservations.add(btnVRDelete2);
		
		lblVRLicensePlate3 = new JLabel("License Plate: ");
		lblVRLicensePlate3.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate3.setBounds(10, 139, 135, 52);
		panelViewReservations.add(lblVRLicensePlate3);
		
		lblVRPassword3 = new JLabel("Password: ");
		lblVRPassword3.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword3.setBounds(147, 139, 135, 52);
		panelViewReservations.add(lblVRPassword3);
		
		lblVRCheckInOut3 = new JLabel("Check In/Out: ");
		lblVRCheckInOut3.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut3.setBounds(286, 139, 135, 52);
		panelViewReservations.add(lblVRCheckInOut3);
		
		lblVRDate3 = new JLabel("Date: ");
		lblVRDate3.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate3.setBounds(425, 139, 110, 52);
		panelViewReservations.add(lblVRDate3);
		
		labelVRSpace3 = new JLabel("Space: ");
		labelVRSpace3.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace3.setBounds(535, 139, 100, 52);
		panelViewReservations.add(labelVRSpace3);
		
		JButton btnVRDelete3 = new JButton("Delete");
		btnVRDelete3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(2) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(2));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete3.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete3.setBounds(635, 151, 89, 23);
		panelViewReservations.add(btnVRDelete3);
		
		lblVRLicensePlate4 = new JLabel("License Plate: ");
		lblVRLicensePlate4.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate4.setBounds(10, 189, 135, 52);
		panelViewReservations.add(lblVRLicensePlate4);
		
		lblVRPassword4 = new JLabel("Password: ");
		lblVRPassword4.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword4.setBounds(147, 189, 135, 52);
		panelViewReservations.add(lblVRPassword4);
		
		lblVRCheckInOut4 = new JLabel("Check In/Out: ");
		lblVRCheckInOut4.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut4.setBounds(286, 189, 135, 52);
		panelViewReservations.add(lblVRCheckInOut4);
		
		lblVRDate4 = new JLabel("Date: ");
		lblVRDate4.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate4.setBounds(425, 189, 110, 52);
		panelViewReservations.add(lblVRDate4);
		
		labelVRSpace4 = new JLabel("Space: ");
		labelVRSpace4.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace4.setBounds(535, 189, 100, 52);
		panelViewReservations.add(labelVRSpace4);
		
		JButton btnVRDelete4 = new JButton("Delete");
		btnVRDelete4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(3) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(3));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete4.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete4.setBounds(635, 201, 89, 23);
		panelViewReservations.add(btnVRDelete4);
		
		lblVRLicensePlate5 = new JLabel("License Plate: ");
		lblVRLicensePlate5.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate5.setBounds(10, 238, 135, 52);
		panelViewReservations.add(lblVRLicensePlate5);
		
		lblVRPassword5 = new JLabel("Password: ");
		lblVRPassword5.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword5.setBounds(147, 238, 135, 52);
		panelViewReservations.add(lblVRPassword5);
		
		lblVRCheckInOut5 = new JLabel("Check In/Out: ");
		lblVRCheckInOut5.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut5.setBounds(286, 238, 135, 52);
		panelViewReservations.add(lblVRCheckInOut5);
		
		lblVRDate5 = new JLabel("Date: ");
		lblVRDate5.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate5.setBounds(425, 238, 110, 52);
		panelViewReservations.add(lblVRDate5);
		
		labelVRSpace5 = new JLabel("Space: ");
		labelVRSpace5.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace5.setBounds(535, 238, 100, 52);
		panelViewReservations.add(labelVRSpace5);
		
		JButton btnVRDelete5 = new JButton("Delete");
		btnVRDelete5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(4) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(4));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete5.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete5.setBounds(635, 250, 89, 23);
		panelViewReservations.add(btnVRDelete5);
		
		lblVRLicensePlate6 = new JLabel("License Plate: ");
		lblVRLicensePlate6.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate6.setBounds(10, 289, 135, 52);
		panelViewReservations.add(lblVRLicensePlate6);
		
		lblVRPassword6 = new JLabel("Password: ");
		lblVRPassword6.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword6.setBounds(147, 289, 135, 52);
		panelViewReservations.add(lblVRPassword6);
		
		lblVRCheckInOut6 = new JLabel("Check In/Out: ");
		lblVRCheckInOut6.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut6.setBounds(286, 289, 135, 52);
		panelViewReservations.add(lblVRCheckInOut6);
		
		lblVRDate6 = new JLabel("Date: ");
		lblVRDate6.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate6.setBounds(425, 289, 110, 52);
		panelViewReservations.add(lblVRDate6);
		
		labelVRSpace6 = new JLabel("Space: ");
		labelVRSpace6.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace6.setBounds(535, 289, 100, 52);
		panelViewReservations.add(labelVRSpace6);
		
		JButton btnVRDelete6 = new JButton("Delete");
		btnVRDelete6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(5) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(5));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete6.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete6.setBounds(635, 301, 89, 23);
		panelViewReservations.add(btnVRDelete6);
		
		lblVRLicensePlate7 = new JLabel("License Plate: ");
		lblVRLicensePlate7.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRLicensePlate7.setBounds(10, 340, 135, 52);
		panelViewReservations.add(lblVRLicensePlate7);
		
		lblVRPassword7 = new JLabel("Password: ");
		lblVRPassword7.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRPassword7.setBounds(147, 340, 135, 52);
		panelViewReservations.add(lblVRPassword7);
		
		lblVRCheckInOut7 = new JLabel("Check In/Out: ");
		lblVRCheckInOut7.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRCheckInOut7.setBounds(286, 340, 135, 52);
		panelViewReservations.add(lblVRCheckInOut7);
		
		lblVRDate7 = new JLabel("Date: ");
		lblVRDate7.setFont(new Font("Candara", Font.PLAIN, 13));
		lblVRDate7.setBounds(425, 340, 110, 52);
		panelViewReservations.add(lblVRDate7);
		
		labelVRSpace7 = new JLabel("Space: ");
		labelVRSpace7.setFont(new Font("Candara", Font.PLAIN, 13));
		labelVRSpace7.setBounds(535, 340, 100, 52);
		panelViewReservations.add(labelVRSpace7);
		
		JButton btnVRDelete7 = new JButton("Delete");
		btnVRDelete7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(VRCurrentPlates.elementAt(6) != "")
				{
					try
					{
						frontEnd.deleteUser(VRCurrentPlates.get(6));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRDelete7.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRDelete7.setBounds(635, 352, 89, 23);
		panelViewReservations.add(btnVRDelete7);
		
		JButton btnVRNext = new JButton("Next");
		btnVRNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VRpage++;
				if(VRpage > VRmaxPage)
					VRpage = 1;
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVRNext.setFont(new Font("Candara", Font.PLAIN, 17));
		btnVRNext.setBounds(513, 414, 110, 23);
		panelViewReservations.add(btnVRNext);
		
		JButton buttonVRPrevious = new JButton("Previous");
		buttonVRPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VRpage--;
				if(VRpage < 1)
					VRpage = VRmaxPage;
				
				try {
					populateViewReservations();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		buttonVRPrevious.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonVRPrevious.setBounds(111, 414, 110, 23);
		panelViewReservations.add(buttonVRPrevious);
		
		JLabel lblVRTitle = new JLabel("VIEW/MODIFY RESERVATIONS");
		lblVRTitle.setFont(new Font("Candara", Font.PLAIN, 27));
		lblVRTitle.setBounds(194, 9, 345, 34);
		panelViewReservations.add(lblVRTitle);
		
		/*JLabel*/ lblVRPageNum = new JLabel();
		lblVRPageNum.setBounds(332, 418, 70, 14);
		panelViewReservations.add(lblVRPageNum);
		
		panelSetParking = new JPanel();
		contentPane.add(panelSetParking, "name_56487391656353");
		panelSetParking.setLayout(null);
		
		JButton buttonSPBack = new JButton("Back");
		//Go back to admin main menu from set parking
		buttonSPBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				panelSetParking.setVisible(false);
				panelAdminMainMenu.setVisible(true);
			}
		});
		buttonSPBack.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonSPBack.setBounds(10, 10, 70, 25);
		panelSetParking.add(buttonSPBack);
		
		JComboBox comboBoxSPLevel = new JComboBox();
		comboBoxSPLevel.setBounds(71, 122, 100, 25);
		panelSetParking.add(comboBoxSPLevel);
		
		JComboBox comboBoxSPRow = new JComboBox();
		comboBoxSPRow.setBounds(266, 122, 100, 25);
		panelSetParking.add(comboBoxSPRow);
		
		JComboBox comboBoxSPSpace = new JComboBox();
		comboBoxSPSpace.setBounds(71, 212, 100, 25);
		panelSetParking.add(comboBoxSPSpace);
		
		JLabel labelSPLevel = new JLabel("Level");
		labelSPLevel.setFont(new Font("Candara", Font.PLAIN, 17));
		labelSPLevel.setBounds(71, 100, 46, 14);
		panelSetParking.add(labelSPLevel);
		
		JLabel labelSPRow = new JLabel("Row");
		labelSPRow.setFont(new Font("Candara", Font.PLAIN, 17));
		labelSPRow.setBounds(266, 100, 46, 14);
		panelSetParking.add(labelSPRow);
		
		JLabel labelSPSpace = new JLabel("Space");
		labelSPSpace.setFont(new Font("Candara", Font.PLAIN, 17));
		labelSPSpace.setBounds(71, 190, 46, 14);
		panelSetParking.add(labelSPSpace);
		
		textFieldSPPrice = new JTextField();
		textFieldSPPrice.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldSPPrice.setText("Enter Price Here");
		textFieldSPPrice.setBounds(266, 212, 100, 25);
		panelSetParking.add(textFieldSPPrice);
		textFieldSPPrice.setColumns(10);
		
		JLabel labelSPPrice = new JLabel("Price");
		labelSPPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		labelSPPrice.setBounds(266, 190, 46, 14);
		panelSetParking.add(labelSPPrice);
		
		JButton btnSPSubmit = new JButton("Submit");
		btnSPSubmit.setFont(new Font("Candara", Font.PLAIN, 17));
		btnSPSubmit.setBounds(176, 317, 89, 23);
		panelSetParking.add(btnSPSubmit);
		
		panelSetSections = new JPanel();
		panelSetSections.setLayout(null);
		contentPane.add(panelSetSections, "name_434911317441442");
		
		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelSetSections.setVisible(false);
				panelAdminMainMenu.setVisible(true);
			}
		});
		button.setFont(new Font("Candara", Font.PLAIN, 17));
		button.setBounds(10, 10, 70, 25);
		panelSetSections.add(button);
		
		JComboBox comboBoxModifyLevels = new JComboBox();
		comboBoxModifyLevels.setBounds(57, 156, 100, 25);
		panelSetSections.add(comboBoxModifyLevels);
		
		JLabel label = new JLabel("Level");
		label.setFont(new Font("Candara", Font.PLAIN, 17));
		label.setBounds(57, 129, 100, 25);
		panelSetSections.add(label);
		
		textFieldModifyPrice = new JTextField();
		textFieldModifyPrice.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldModifyPrice.setColumns(10);
		textFieldModifyPrice.setBounds(202, 246, 100, 25);
		panelSetSections.add(textFieldModifyPrice);
		
		JButton buttonModify = new JButton("Modify");
		buttonModify.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonModify.setBounds(57, 347, 89, 23);
		panelSetSections.add(buttonModify);
		
		JLabel lblOfRows = new JLabel("# of Rows");
		lblOfRows.setFont(new Font("Candara", Font.PLAIN, 17));
		lblOfRows.setBounds(202, 128, 100, 25);
		panelSetSections.add(lblOfRows);
		
		JLabel lblOfSpaces = new JLabel("# of Spaces");
		lblOfSpaces.setFont(new Font("Candara", Font.PLAIN, 17));
		lblOfSpaces.setBounds(57, 219, 100, 25);
		panelSetSections.add(lblOfSpaces);
		
		JLabel lblModifyPrice = new JLabel("Default Price");
		lblModifyPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		lblModifyPrice.setBounds(202, 219, 100, 25);
		panelSetSections.add(lblModifyPrice);
		
		textFieldModifyRows = new JTextField();
		textFieldModifyRows.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldModifyRows.setColumns(10);
		textFieldModifyRows.setBounds(202, 157, 100, 25);
		panelSetSections.add(textFieldModifyRows);
		
		textFieldModifySpaces = new JTextField();
		textFieldModifySpaces.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldModifySpaces.setColumns(10);
		textFieldModifySpaces.setBounds(57, 246, 100, 25);
		panelSetSections.add(textFieldModifySpaces);
		
		JLabel lblModifyExisting = new JLabel("Modify Existing Section");
		lblModifyExisting.setHorizontalAlignment(SwingConstants.CENTER);
		lblModifyExisting.setFont(new Font("Candara", Font.PLAIN, 17));
		lblModifyExisting.setBounds(57, 68, 245, 31);
		panelSetSections.add(lblModifyExisting);
		
		JButton buttonAddNew = new JButton("Add");
		buttonAddNew.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonAddNew.setBounds(508, 347, 89, 23);
		panelSetSections.add(buttonAddNew);
		
		textFieldNewPrice = new JTextField();
		textFieldNewPrice.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldNewPrice.setColumns(10);
		textFieldNewPrice.setBounds(572, 246, 100, 25);
		panelSetSections.add(textFieldNewPrice);
		
		JLabel lblNewPrice = new JLabel("Default Price");
		lblNewPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		lblNewPrice.setBounds(572, 219, 100, 25);
		panelSetSections.add(lblNewPrice);
		
		textFieldNewRows = new JTextField();
		textFieldNewRows.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldNewRows.setColumns(10);
		textFieldNewRows.setBounds(572, 157, 100, 25);
		panelSetSections.add(textFieldNewRows);
		
		JLabel lblNewRows = new JLabel("# of Rows");
		lblNewRows.setFont(new Font("Candara", Font.PLAIN, 17));
		lblNewRows.setBounds(572, 128, 100, 25);
		panelSetSections.add(lblNewRows);
		
		textFieldNewSpaces = new JTextField();
		textFieldNewSpaces.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldNewSpaces.setColumns(10);
		textFieldNewSpaces.setBounds(427, 246, 100, 25);
		panelSetSections.add(textFieldNewSpaces);
		
		JLabel lblNewSpaces = new JLabel("# of Spaces");
		lblNewSpaces.setFont(new Font("Candara", Font.PLAIN, 17));
		lblNewSpaces.setBounds(427, 219, 100, 25);
		panelSetSections.add(lblNewSpaces);
		
		JLabel lblNewLevel = new JLabel("Level Name");
		lblNewLevel.setFont(new Font("Candara", Font.PLAIN, 17));
		lblNewLevel.setBounds(427, 129, 100, 25);
		panelSetSections.add(lblNewLevel);
		
		JLabel lblAddNewSection = new JLabel("Add New Section");
		lblAddNewSection.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewSection.setFont(new Font("Candara", Font.PLAIN, 17));
		lblAddNewSection.setBounds(427, 68, 245, 31);
		panelSetSections.add(lblAddNewSection);
		
		textFieldNewLevel = new JTextField();
		textFieldNewLevel.setFont(new Font("Candara", Font.PLAIN, 13));
		textFieldNewLevel.setColumns(10);
		textFieldNewLevel.setBounds(427, 158, 100, 25);
		panelSetSections.add(textFieldNewLevel);
		
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonDelete.setBounds(212, 347, 89, 23);
		panelSetSections.add(buttonDelete);
		
		panelParkNow = new JPanel();
		panelParkNow.setLayout(null);
		contentPane.add(panelParkNow, "name_2713267510600");
		
		comboBoxPNLevel = new JComboBox(frontEnd.getLevels());
		comboBoxPNLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox cb = (JComboBox)e.getSource();
				String mapLevel = (String)cb.getSelectedItem();
				String mapRow = (String)comboBoxPNRow.getSelectedItem();
				String mapSpace = (String)comboBoxPNSpace.getSelectedItem();
				//Update the map image
				updateMapImage(mapLevel, "PN");
				//Update the parking price displayed
				
				try {
					comboBoxPNSpace.setModel(frontEnd.getSpaces(mapLevel, mapRow));
					comboBoxPNRow.setModel(frontEnd.getRows(mapLevel));
					updateParkingPrice(mapLevel, mapRow, mapSpace, "PN");
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		comboBoxPNLevel.setBounds(20, 72, 100, 25);
		panelParkNow.add(comboBoxPNLevel);
		
		
		JButton buttonPNBack = new JButton("Back");
		buttonPNBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				clearPanelParkNow();
				panelParkNow.setVisible(false);
				panelCustomerMainMenu.setVisible(true);
			}
		});
		buttonPNBack.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonPNBack.setBounds(10, 10, 70, 25);
		panelParkNow.add(buttonPNBack);
		
		lblPNMap = new JLabel("");
		Image PNMap = new ImageIcon(this.getClass().getResource("/LevelA.jpg")).getImage();
		lblPNMap.setIcon(new ImageIcon(PNMap));
		Border PNMapBorder = LineBorder.createBlackLineBorder();
		lblPNMap.setBorder(PNMapBorder);
		lblPNMap.setBounds(281, 56, 445, 300);
		panelParkNow.add(lblPNMap);
		
		comboBoxPNRow = new JComboBox(frontEnd.getRows("A"));
		comboBoxPNRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox cb = (JComboBox)e.getSource();
				String mapRow = (String)cb.getSelectedItem();
				String mapLevel = (String)comboBoxPNLevel.getSelectedItem();
				String mapSpace = (String)comboBoxPNSpace.getSelectedItem();
				
				try {
					comboBoxPNSpace.setModel(frontEnd.getSpaces(mapLevel, mapRow));
					updateParkingPrice(mapLevel,mapRow,mapSpace,"PN");
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		comboBoxPNRow.setBounds(160, 72, 100, 25);
		panelParkNow.add(comboBoxPNRow);
		
		JLabel labelPNLevel = new JLabel("Level");
		labelPNLevel.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNLevel.setBounds(20, 59, 46, 14);
		panelParkNow.add(labelPNLevel);
		
		JLabel labelPNRow = new JLabel("Row");
		labelPNRow.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNRow.setBounds(160, 59, 46, 14);
		panelParkNow.add(labelPNRow);
		
		comboBoxPNSpace = new JComboBox(frontEnd.getSpaces("A", "1"));
		comboBoxPNSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox cb = (JComboBox)e.getSource();
				String mapSpace = (String)cb.getSelectedItem();
				String mapRow = (String)comboBoxPNRow.getSelectedItem();
				String mapLevel = (String)comboBoxPNLevel.getSelectedItem();
				try {
					updateParkingPrice(mapLevel,mapRow,mapSpace,"PN");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBoxPNSpace.setBounds(20, 138, 100, 25);
		panelParkNow.add(comboBoxPNSpace);
		
		JLabel labelPNSpace = new JLabel("Space");
		labelPNSpace.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNSpace.setBounds(20, 123, 46, 14);
		panelParkNow.add(labelPNSpace);
		
		comboBoxPNDuration = new JComboBox(frontEnd.getDurations());
		comboBoxPNDuration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String mapLevel = comboBoxPNLevel.getSelectedItem().toString();
				String mapRow = comboBoxPNRow.getSelectedItem().toString();
				String mapSpace = comboBoxPNSpace.getSelectedItem().toString();
				try {
					updateParkingPrice(mapLevel, mapRow, mapSpace, "PN");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		comboBoxPNDuration.setBounds(160, 138, 100, 25);
		panelParkNow.add(comboBoxPNDuration);
		
		JLabel labelPNDuration = new JLabel("Duration");
		labelPNDuration.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNDuration.setBounds(159, 119, 63, 22);
		panelParkNow.add(labelPNDuration);
		
		textFieldPNLicensePlate = new JTextField();
		textFieldPNLicensePlate.setColumns(10);
		textFieldPNLicensePlate.setBounds(92, 204, 100, 25);
		panelParkNow.add(textFieldPNLicensePlate);
		textFieldPNLicensePlate.setDocument(new JTextFieldLimit(9));
		
		JLabel labelPNLicensePlate = new JLabel("License Plate");
		labelPNLicensePlate.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNLicensePlate.setBounds(95, 179, 94, 22);
		panelParkNow.add(labelPNLicensePlate);
		
		JButton buttonPNSubmit = new JButton("Submit");
		buttonPNSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(textFieldPNLicensePlate.getText().equals(""))
				{
					JOptionPane.showMessageDialog(panelParkNow, "License plate field is empty.");
				}
				else
				{
					try 
					{
						//Submits the park now form to send to the DB
						submitParkNowForm();
						//Populates the ticket
						populateConfirmationInfo(0);
						//Clears the information submitted by the user
						clearPanelParkNow();
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					panelConfirmation.setVisible(true);
					panelParkNow.setVisible(false);
				}
			}
		});
		buttonPNSubmit.setOpaque(true);
		buttonPNSubmit.setForeground(Color.BLACK);
		buttonPNSubmit.setFont(new Font("Candara", Font.PLAIN, 17));
		buttonPNSubmit.setBackground(Color.GREEN);
		buttonPNSubmit.setBounds(92, 274, 89, 23);
		panelParkNow.add(buttonPNSubmit);
		
		labelPNPrice = new JLabel();
		if(comboBoxPNLevel.getItemCount()==0||comboBoxPNRow.getItemCount()==0||comboBoxPNSpace.getItemCount()==0)
		{
			labelPNPrice.setText("0.00");
		}
		else
		{
			updateParkingPrice(comboBoxPNLevel.getItemAt(0).toString(), comboBoxPNRow.getItemAt(0).toString(), comboBoxPNSpace.getItemAt(0).toString(), "PN");
		}
		
		labelPNPrice.setFont(new Font("Candara", Font.PLAIN, 17));
		labelPNPrice.setBounds(92, 240, 145, 25);
		panelParkNow.add(labelPNPrice);
	}
}
