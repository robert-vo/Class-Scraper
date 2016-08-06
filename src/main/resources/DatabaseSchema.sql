	use class;

DROP TABLE IF EXISTS CORE_CLASS;
DROP TABLE IF EXISTS CLASS;
DROP TABLE IF EXISTS TERMS;
DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS BUILDING;
DROP TABLE IF EXISTS CLASS_INFORMATION;
DROP TABLE IF EXISTS CORE;

CREATE TABLE DEPARTMENT (
	DEPARTMENT_Abbreviation	VARCHAR(5) PRIMARY KEY,
    DEPARTMENT_Name			varchar(75) NOT NULL
);

CREATE TABLE BUILDING (
	BUILDING_ID				INT,
	BUILDING_Abbreviation	VARCHAR(5) PRIMARY KEY,
    BUILDING_Name			VARCHAR(100) NOT NULL
);

CREATE TABLE TERMS (
	Term_ID		INT PRIMARY KEY,
    Year		INT NOT NULL,
    Semester	ENUM('Fall', 'Summer', 'Spring')
);

CREATE TABLE CORE(
	CORE_ID				int primary key,
    CORE_TITLE			varchar(100),
    HOURS_REQUIRED		int
);

CREATE TABLE CORE_CLASS(
	core_class_id			int primary key auto_increment,
	DEPARTMENT_Abbreviation varchar(5),
    department_crn			varchar(5),
    core_id					int,
    foreign key(core_id) references core(core_id)
);

CREATE TABLE CLASS_INFORMATION (
	DEPARTMENT			VARCHAR(5) NOT NULL,
    DEPARTMENT_CRN 		VARCHAR(5) NOT NULL,
    CLASS_DESCRIPTION	VARCHAR(1000),
	CLASS_TITLE			VARCHAR(100),
    CREDIT_HOURS		int,
    CORE				int null,
    PRIMARY KEY(DEPARTMENT, DEPARTMENT_CRN),
    foreign key(CORE) REFERENCES CORE(CORE_ID)
		ON DELETE NO ACTION
);

CREATE TABLE CLASS (
	Class_ID            INT     PRIMARY KEY AUTO_INCREMENT,
    Term_ID             INT,
    CRN                 INT     ,
    DEPARTMENT          VARCHAR(5)    NOT NULL,
    DEPARTMENT_CRN      VARCHAR(5)    NOT NULL,
    Status              ENUM('Open', 'Closed') ,
    ATTRIBUTES          VARCHAR(100),
    START_DATE          DATE,
    END_DATE            DATE,
    START_TIME          TIME,
    END_TIME            TIME,
    INSTRUCTOR          VARCHAR(100),
    INSTRUCTOR_EMAIL    VARCHAR(100),
    LOCATION            VARCHAR(100),
    BUILDING_ABBV       VARCHAR(10),
    BUILDING_ROOM_NUM   VARCHAR(15),
    FORMAT              VARCHAR(100),
    DURATION            VARCHAR(100),
    SESSION             VARCHAR(100),
    COMPONENT           VARCHAR(100),
    SYLLABUS            VARCHAR(200),
    SEATS_TAKEN 		INT,
    SEATS_AVAILABLE		INT,
    SEATS_TOTAL			INT,
    MONDAY				BOOLEAN DEFAULT FALSE,
    TUESDAY				BOOLEAN DEFAULT FALSE,
    WEDNESDAY			BOOLEAN DEFAULT FALSE,
    THURSDAY			BOOLEAN DEFAULT FALSE,
    FRIDAY				BOOLEAN DEFAULT FALSE,
	SATURDAY			BOOLEAN DEFAULT FALSE,
    SUNDAY				BOOLEAN DEFAULT FALSE,
    LAST_UPDATED_AT		TIMESTAMP default current_timestamp 
		on update current_timestamp,
    foreign key (Term_ID)
		REFERENCES TERMS(Term_ID)
        ON DELETE no action,
    foreign key (DEPARTMENT)
        REFERENCES DEPARTMENT(DEPARTMENT_Abbreviation)
        ON DELETE no action,
	foreign key (BUILDING_Abbv)
		REFERENCES BUILDING(BUILDING_Abbreviation)
        ON DELETE no action,
	foreign key (DEPARTMENT, DEPARTMENT_CRN)
		REFERENCES CLASS_INFORMATION(DEPARTMENT, DEPARTMENT_CRN)
);

insert into TERMS values(1950, 2015, 'Spring');
insert into TERMS values(1960, 2015, 'Summer');
insert into TERMS values(1970, 2015, 'Fall');
insert into TERMS values(1980, 2016, 'Spring');
insert into TERMS values(1990, 2016, 'Summer');
insert into TERMS values(2000, 2016, 'Fall');
insert into TERMS values(2010, 2017, 'Spring');
insert into TERMS values(2020, 2017, 'Summer');
insert into TERMS values(2030, 2017, 'Fall');
insert into TERMS values(2040, 2018, 'Spring');
insert into TERMS values(2050, 2018, 'Summer');
insert into TERMS values(2060, 2018, 'Fall');
insert into TERMS values(2070, 2019, 'Spring');
insert into TERMS values(2080, 2019, 'Summer');
insert into TERMS values(2090, 2019, 'Fall');
insert into TERMS values(2100, 2020, 'Spring');
insert into TERMS values(2110, 2020, 'Summer');
insert into TERMS values(2120, 2020, 'Fall');

insert into DEPARTMENT values("AAS","African American Studies");
insert into DEPARTMENT values("ACCT","Accounting");
insert into DEPARTMENT values("AFSC","Air Force Science");
insert into DEPARTMENT values("AMER","American Cultures");
insert into DEPARTMENT values("ANTH","Anthropology");
insert into DEPARTMENT values("ARAB","Arabic");
insert into DEPARTMENT values("ARAF","Architect Affiliated St");
insert into DEPARTMENT values("ARCH","Architecture");
insert into DEPARTMENT values("ARED","Art Education");
insert into DEPARTMENT values("ARLD","Arts Leadership");
insert into DEPARTMENT values("ARRE","Arch Reciprocal Exch");
insert into DEPARTMENT values("ART","Art");
insert into DEPARTMENT values("ARTH","Art History");
insert into DEPARTMENT values("ASLI","Amer Sign Lang Interpre");
insert into DEPARTMENT values("ATP","Athletic Training Progr");
insert into DEPARTMENT values("BCHS","Biochemical & Biophys Sci");
insert into DEPARTMENT values("BIOE","Biomedical Engineering");
insert into DEPARTMENT values("BIOL","Biology");
insert into DEPARTMENT values("BTEC","Biotechnology");
insert into DEPARTMENT values("BUAF","Business Affiliated St");
insert into DEPARTMENT values("BURE","Business Reciprocal Exc");
insert into DEPARTMENT values("BZAN","Business Analytics");
insert into DEPARTMENT values("CCS","Comparative Cult Studies");
insert into DEPARTMENT values("CHEE","Chemical Engineering");
insert into DEPARTMENT values("CHEM","Chemistry");
insert into DEPARTMENT values("CHNS","Chinese");
insert into DEPARTMENT values("CIS","Computer Information Sys");
insert into DEPARTMENT values("CIVE","Civil & Environm Engr");
insert into DEPARTMENT values("CLAS","Classics");
insert into DEPARTMENT values("CNST","Construction Technology");
insert into DEPARTMENT values("COMD","Communication Disorders");
insert into DEPARTMENT values("COMM","Communication");
insert into DEPARTMENT values("COOP","Cooperative Engineering");
insert into DEPARTMENT values("CORE","Core Curriculum");
insert into DEPARTMENT values("COSC","Computer Science");
insert into DEPARTMENT values("CUIN","Curriculum & Instruction");
insert into DEPARTMENT values("CUST","Cultural & Urban Studies");
insert into DEPARTMENT values("DAN","Dance");
insert into DEPARTMENT values("DIGM","Digital Media");
insert into DEPARTMENT values("ECE","Electrical and Comp Engr");
insert into DEPARTMENT values("ECON","Economics");
insert into DEPARTMENT values("EDAF","Education Affil Studies");
insert into DEPARTMENT values("EDRS","Educational Research");
insert into DEPARTMENT values("EDUC","Education");
insert into DEPARTMENT values("EGRP","Engineering PROMES");
insert into DEPARTMENT values("ELCS","Educ Ldrshp & Cultural St");
insert into DEPARTMENT values("ELED","Elementary Education");
insert into DEPARTMENT values("ELET","Electrical-Electron Tec");
insert into DEPARTMENT values("ENGI","Engineering");
insert into DEPARTMENT values("ENGL","English");
insert into DEPARTMENT values("ENRE","Engineering Recipr Exc");
insert into DEPARTMENT values("ENRG","Energy & Sustainability");
insert into DEPARTMENT values("ENTR","Entrepreneurship");
insert into DEPARTMENT values("EPSY","Educational Psychology");
insert into DEPARTMENT values("FINA","Finance");
insert into DEPARTMENT values("FORE","Foresight");
insert into DEPARTMENT values("FREN","French");
insert into DEPARTMENT values("GENB","General Business Admin");
insert into DEPARTMENT values("GEOL","Geosciences");
insert into DEPARTMENT values("GERM","German");
insert into DEPARTMENT values("GIS" ,"Global and International Studies");
insert into DEPARTMENT values("GREK","Greek");
insert into DEPARTMENT values("GRET","Global Retailing");
insert into DEPARTMENT values("HDCS","Hum Develop & Consum Sc");
insert into DEPARTMENT values("HDFS","Hum Develop & Fam Stds");
insert into DEPARTMENT values("HIND","Hindi");
insert into DEPARTMENT values("HIST","History");
insert into DEPARTMENT values("HLT","Health Education");
insert into DEPARTMENT values("HON","Honors College");
insert into DEPARTMENT values("HRAF","HRM Affiliated Studies");
insert into DEPARTMENT values("HRD","Human Resources Developm");
insert into DEPARTMENT values("HRMA","Hotel & Restaurant Mgt");
insert into DEPARTMENT values("HRRE","HRM Reciprocal Exc");
insert into DEPARTMENT values("IART","Interdisciplinary Art");
insert into DEPARTMENT values("IDNS","Interdisciplinary-NSM");
insert into DEPARTMENT values("IGS","International & Global St");
insert into DEPARTMENT values("ILAS","Interdisciplinary-LASS");
insert into DEPARTMENT values("INAR","Interior Arch");
insert into DEPARTMENT values("INDE","Industrial Engineering");
insert into DEPARTMENT values("INDS","Industrial Design");
insert into DEPARTMENT values("INTB","International Business");
insert into DEPARTMENT values("IRW","Integ Reading & Writing");
insert into DEPARTMENT values("ITAL","Italian");
insert into DEPARTMENT values("ITEC","Information Syst Tech");
insert into DEPARTMENT values("JPNS","Japanese");
insert into DEPARTMENT values("JWST","Jewish Studies");
insert into DEPARTMENT values("KIN","Kinesiology");
insert into DEPARTMENT values("LAAF","Law Affiliated Studies");
insert into DEPARTMENT values("LARE","Law Reciprocal Exchange");
insert into DEPARTMENT values("LAST","Latin Am Studies");
insert into DEPARTMENT values("LATN","Latin");
insert into DEPARTMENT values("LAW","Law");
insert into DEPARTMENT values("LSAF","LASS Affiliated Studies");
insert into DEPARTMENT values("LSRE","LASS Reciprocal Exc");
insert into DEPARTMENT values("MANA","Management");
insert into DEPARTMENT values("MARK","Marketing and Entrepren");
insert into DEPARTMENT values("MAS","Mexican Am Studies");
insert into DEPARTMENT values("MATH","Mathematics");
insert into DEPARTMENT values("MECE","Mechanical Engineering");
insert into DEPARTMENT values("MECT","Mechanical Technology");
insert into DEPARTMENT values("MIS","Management Info. Systems");
insert into DEPARTMENT values("MSCI","Military Science");
insert into DEPARTMENT values("MTLS","Materials Engineering");
insert into DEPARTMENT values("MUED","Music Education");
insert into DEPARTMENT values("MUSA","Applied Music");
insert into DEPARTMENT values("MUSI","Music");
insert into DEPARTMENT values("NAVY","Navy");
insert into DEPARTMENT values("NSAF","NSM Affiliated Studies");
insert into DEPARTMENT values("NSRE","NSM Reciprocal Exchange");
insert into DEPARTMENT values("NURS","Nursing");
insert into DEPARTMENT values("NUTR","Nutrition");
insert into DEPARTMENT values("OPTO","Optometry");
insert into DEPARTMENT values("PCEU","Pharmaceutics");
insert into DEPARTMENT values("PCOL","Pharmacology");
insert into DEPARTMENT values("PEB","Physical Edu Basic Instr");
insert into DEPARTMENT values("PEP","Physical Ed Professional");
insert into DEPARTMENT values("PETR","Petroleum Engineering");
insert into DEPARTMENT values("PHAR","Pharmacy");
insert into DEPARTMENT values("PHCA","Pharmacy Practice");
insert into DEPARTMENT values("PHIL","Philosophy");
insert into DEPARTMENT values("PHLA","Pharm Leadership Admin");
insert into DEPARTMENT values("PHLS","Psych Hlth Learn Sci");
insert into DEPARTMENT values("PHOP","Physiological Optics");
insert into DEPARTMENT values("PHYS","Physics");
insert into DEPARTMENT values("POLC","Policy");
insert into DEPARTMENT values("POLS","Political Science");
insert into DEPARTMENT values("PSYC","Psychology");
insert into DEPARTMENT values("PUBL","Public Administration");
insert into DEPARTMENT values("QSS","Quantitative Social Sci");
insert into DEPARTMENT values("RELS","Religious Studies");
insert into DEPARTMENT values("RUSS","Russian");
insert into DEPARTMENT values("SAAF","Affiliated Studies Grad");
insert into DEPARTMENT values("SAER","Systems Anal & Ed Res");
insert into DEPARTMENT values("SARE","Reciprocal Exchange Grad");
insert into DEPARTMENT values("SCLT","Supply Chain Logis Tech");
insert into DEPARTMENT values("SCM","Supply Chain Mgmt");
insert into DEPARTMENT values("SEDE","Secondary Education");
insert into DEPARTMENT values("SOC","Sociology");
insert into DEPARTMENT values("SOCW","Social Work");
insert into DEPARTMENT values("SPAC","Space Architecture");
insert into DEPARTMENT values("SPAN","Spanish");
insert into DEPARTMENT values("STAT","Statistics");
insert into DEPARTMENT values("SUBS","Subsea Engineering");
insert into DEPARTMENT values("TEAF","TECH Affiliated Studies");
insert into DEPARTMENT values("TECH","Technology");
insert into DEPARTMENT values("TELS","TECH Leadership& Superv");
insert into DEPARTMENT values("TEPM","Technology Project Mgt");
insert into DEPARTMENT values("THEA","Theatre");
insert into DEPARTMENT values("TMTH","Technical Mathematics");
insert into DEPARTMENT values("UNIV","University Studies");
insert into DEPARTMENT values("VIET","Vietnamese");
insert into DEPARTMENT values("WCL","World Cultures & Lit");
insert into DEPARTMENT values("WGSS","WomenGendSexualitySt");

insert into BUILDING values(0000, '', '');
insert into BUILDING values(0101, 'CRW', 'Chancellor\'s Residence Wortham House');
insert into BUILDING values(0104, 'P2', 'KUHT Fiber Optics BUILDING');
insert into BUILDING values(0105, 'TVTE', 'KUHT Telephone Equipment');
insert into BUILDING values(0106, 'TV', 'Texas Learning & Computational Center Annex');
insert into BUILDING values(0108, 'P1', 'Cullen Annex Laboratory');
insert into BUILDING values(0111, 'DYN', 'Dynamometer Test Laboratory');
insert into BUILDING values(0115, 'OFGR', 'Office of Governmental Relations');
insert into BUILDING values(0116, 'ACT', 'Safety, Human Factors & Ergonomic Laboratory');
insert into BUILDING values(0117, 'TVFB', 'KUHT TV at Fort Bend - Tower');
insert into BUILDING values(0118, 'WRCH', 'Wortham Residence Coach House');
insert into BUILDING values(0119, 'ACTA', 'Texas Manufacturing Assistance Center');
insert into BUILDING values(0120, 'UHFB', 'Albert and Mamie George BUILDING');
insert into BUILDING values(0121, 'FBA2', 'Brazos Hall');
insert into BUILDING values(0122, 'UBL', 'University Branch Library (UHS-Sugar Land)');
insert into BUILDING values(0125, 'WHS', 'Wortham House Storage');
insert into BUILDING values(0126, 'SA1', 'Sugar Land Annex 1');
insert into BUILDING values(0127, 'SA2', 'Sugar Land Annex 2');
insert into BUILDING values(0128, 'SA3', 'Sugar Land Annex 3');
insert into BUILDING values(0199, 'CRSA', 'Clinical Research Services Annex');
insert into BUILDING values(0400, 'CSS', 'Cougar Sub-Station');
insert into BUILDING values(0401, 'ERP1', 'Energy Research Park 01');
insert into BUILDING values(0402, 'ERP2', 'Energy Research Park 02');
insert into BUILDING values(0403, 'ERP3', 'Energy Research Park 03');
insert into BUILDING values(0404, 'ERP4', 'Conference & Research BUILDING');
insert into BUILDING values(0405, 'ERP5', 'Energy Research Park 05');
insert into BUILDING values(0406, 'ERP6', 'Energy Research Park 06');
insert into BUILDING values(0407, 'ERP7', 'Energy Research Park 07');
insert into BUILDING values(0408, 'ERP8', 'Energy Research Park 08');
insert into BUILDING values(0409, 'ERP9', 'ConocoPhillips Petroleum Engineering BUILDING');
insert into BUILDING values(0410, 'ERP10', 'Energy Research Park 10');
insert into BUILDING values(0411, 'ERP11', 'Energy Research Park 11');
insert into BUILDING values(0413, 'ERP13', 'Energy Research Park 13');
insert into BUILDING values(0414, 'ERP14', 'Energy Research Park 14');
insert into BUILDING values(0415, 'ERP15', 'Energy Device Fabrication Laboratory');
insert into BUILDING values(0419, 'ERPA', 'Energy Research Park Annex');
insert into BUILDING values(0420, 'ERP20', 'Energy Research Park Storage');
insert into BUILDING values(0485, 'STAD', 'TDECU Stadium');
insert into BUILDING values(0486, 'ATL', 'Atmospheric Testing Laboratory');
insert into BUILDING values(0487, 'UCN', 'Student Center North');
insert into BUILDING values(0488, 'SCP1', 'Central Plant Satellite');
insert into BUILDING values(0489, 'KUHA2', 'KUHA - Renters BUILDING');
insert into BUILDING values(0490, 'WCSG', 'Welcome Center Student Garage');
insert into BUILDING values(0491, 'KUHA', 'KUHA - Transmitter BUILDING');
insert into BUILDING values(0492, 'SST', 'Cougar Softball Stadium Ticket Booth');
insert into BUILDING values(0493, 'SS', 'Cougar Softball Stadium');
insert into BUILDING values(0494, 'AAA', 'Agnes Arnold Auditorium');
insert into BUILDING values(0495, 'CPH', 'Cougar Place');
insert into BUILDING values(0496, 'CV2', 'Cougar Village 2');
insert into BUILDING values(0498, 'DTF', 'Bulk Fueling Diesel Tank Farm');
insert into BUILDING values(0499, 'CBB', 'Classroom and Business BUILDING');
insert into BUILDING values(0500, 'BOA', 'Bayou Oaks Apartments');
insert into BUILDING values(0501, 'C', 'Roy G. Cullen');
insert into BUILDING values(0502, 'S', 'Science BUILDING');
insert into BUILDING values(0503, 'T', 'Technology Annex');
insert into BUILDING values(0504, 'CCC', 'Child Care Center');
insert into BUILDING values(0505, 'JDA', 'J. Davis Armistead');
insert into BUILDING values(0506, 'COM', 'Jack J. Valenti School of Communication');
insert into BUILDING values(0507, 'WT', 'Cynthia Woods Mitchell Center for the Arts/Wortham Theatre');
insert into BUILDING values(0508, 'T2', 'College of Technology BUILDING');
insert into BUILDING values(0509, 'L', 'M. D. Anderson Library');
insert into BUILDING values(0513, 'INF2', 'Visitor Information Booth 2');
insert into BUILDING values(0514, 'EPS2', 'Fire & Life Safety - Storage');
insert into BUILDING values(0515, 'PP', 'Central Power Plant');
insert into BUILDING values(0516, 'E', 'Ezekiel W. Cullen');
insert into BUILDING values(0517, 'A', 'Cullen Performance Hall');
insert into BUILDING values(0518, 'CLA', 'Calhoun Lofts Apartments');
insert into BUILDING values(0519, 'UHPD', 'DEPARTMENT of Public Safety - UH Police');
insert into BUILDING values(0520, 'MSM', 'Rebecca & John J. Moores School of Music');
insert into BUILDING values(0521, 'GS', 'Grounds Storage BUILDING');
insert into BUILDING values(0522, 'CRWC', 'Campus Recreation & Wellness Center');
insert into BUILDING values(0523, 'AGL', 'Science & Engineering Annex');
insert into BUILDING values(0524, 'SSC', 'Student Service Center 1');
insert into BUILDING values(0525, 'HC', 'Health Center');
insert into BUILDING values(0526, 'SS2', 'Student Service Center 2');
insert into BUILDING values(0527, 'EPS3', 'EHRM1');
insert into BUILDING values(0528, 'MH', 'LeRoy & Lucile Melcher Hall');
insert into BUILDING values(0529, 'SEC', 'Science & Engineering Classroom BUILDING');
insert into BUILDING values(0531, 'HP', 'Hofheinz Pavilion');
insert into BUILDING values(0532, 'GAR', 'Susanna Garrison Gymnasium');
insert into BUILDING values(0533, 'MEL', 'Melcher Gymnasium/Charter School');
insert into BUILDING values(0534, 'H', 'Fred J. Heyne');
insert into BUILDING values(0535, 'EPS1', 'EHRM2');
insert into BUILDING values(0536, 'CPB', 'LeRoy & Lucile Melcher Center for Public Broadcasting');
insert into BUILDING values(0537, 'BL', 'Bates Law');
insert into BUILDING values(0538, 'TU2', 'Teaching Unit 2 BUILDING');
insert into BUILDING values(0539, 'KH', 'Max Krost Hall');
insert into BUILDING values(0540, 'LL', 'John M. O\'Quinn Law Library');
insert into BUILDING values(0541, 'UTS', 'Utility Tunnel System');
insert into BUILDING values(0542, 'SPA', 'South Park Annex');
insert into BUILDING values(0543, 'ARC', 'Gerald D. Hines College of Architecture');
insert into BUILDING values(0544, 'CEMO', 'Michael J. Cemo Hall');
insert into BUILDING values(0545, 'SERC', 'Science & Engineering Research Center');
insert into BUILDING values(0546, 'EPG', 'East Parking Garage');
insert into BUILDING values(0547, 'PGH', 'Philip Guthrie Hoffman Hall');
insert into BUILDING values(0548, 'AMB', 'Athletics Maintenance BUILDING');
insert into BUILDING values(0549, 'SW', 'Graduate College of Social Work');
insert into BUILDING values(0550, 'SR', 'Science and Research 1');
insert into BUILDING values(0551, 'SR2', 'Science and Research 2');
insert into BUILDING values(0552, 'BKD', 'Burdette Keeland Jr. Design & Exploration Center');
insert into BUILDING values(0553, 'WC', 'Welcome Center & Parking Garage');
insert into BUILDING values(0555, 'LH', 'Law Residence Hall');
insert into BUILDING values(0556, 'SH', 'Settegast Residence Hall');
insert into BUILDING values(0557, 'BH', 'Bates Residence Hall');
insert into BUILDING values(0558, 'TH', 'Taub Residence Hall');
insert into BUILDING values(0559, 'OB', 'E. E. Oberholtzer Residence Hall');
insert into BUILDING values(0560, 'UPD', 'UH-DPS Parking Enforcement');
insert into BUILDING values(0561, 'CRWA', 'CRWC Annex');
insert into BUILDING values(0562, 'ADB', 'A. D. Bruce Religion Center');
insert into BUILDING values(0563, 'CV', 'Cougar Village');
insert into BUILDING values(0564, 'F', 'Lamar Fleming, Jr.');
insert into BUILDING values(0565, 'UC', 'Student Center South');
insert into BUILDING values(0567, 'UCS', 'Student Center Satellite');
insert into BUILDING values(0568, 'CSD', 'Justin Dart Jr. Center for Students with DisABILITIES');
insert into BUILDING values(0569, 'CULLO', 'Cullen Oaks Apartments');
insert into BUILDING values(0570, 'BATC', 'Athletics Batting Cage');
insert into BUILDING values(0572, 'GSS', 'General Services Storage BUILDING');
insert into BUILDING values(0573, 'ALUM', 'Alumni Center');
insert into BUILDING values(0574, 'ATH2', 'Athletic Center');
insert into BUILDING values(0575, 'CO', 'Cambridge Oaks Apartments');
insert into BUILDING values(0576, 'STL', 'Science Teaching Laboratory BUILDING');
insert into BUILDING values(0578, 'AH', 'Agnes Arnold Hall');
insert into BUILDING values(0579, 'D', 'Cullen College of Engineering 1');
insert into BUILDING values(0580, 'D2', 'Engineering Lecture Hall');
insert into BUILDING values(0581, 'D3', 'Cullen College of Engineering 2');
insert into BUILDING values(0582, 'BF', 'Cougar Baseball Field');
insert into BUILDING values(0583, 'BFT', 'Cougar Baseball Field Ticket Booth');
insert into BUILDING values(0584, 'MR', 'Moody Towers Residence Halls');
insert into BUILDING values(0585, 'GEN', 'General Services BUILDING');
insert into BUILDING values(0586, 'CAM', 'Isabel C. Cameron');
insert into BUILDING values(0587, 'FH', 'Stephen Power Farish Hall');
insert into BUILDING values(0588, 'M', 'Charles F. McElhinney Hall');
insert into BUILDING values(0589, 'FA', 'Fine Arts BUILDING');
insert into BUILDING values(0590, 'CHC', 'Conrad Hilton College of Hotel & Restaurant Management');
insert into BUILDING values(0591, 'INF', 'Visitor Information Booth 1');
insert into BUILDING values(0592, 'HBS', 'Health and Biomedical Sciences Center');
insert into BUILDING values(0593, 'HSC', 'University of Houston Science Center');
insert into BUILDING values(0594, 'EERC', 'Engineering Education Resource Center');
insert into BUILDING values(0595, 'RES', 'Chinese Star Restaurant');
insert into BUILDING values(0596, 'CC', 'Computing Center');
insert into BUILDING values(0597, 'SPG', 'Stadium Parking Garage');
insert into BUILDING values(0598, 'CRS', 'Clinical Research Services Center');
insert into BUILDING values(0599, 'CLTF', 'Carl Lewis International Track & Field Complex');
insert into BUILDING values(0701, 'PHA', 'College of Pharmacy Texas Medical Center');
insert into BUILDING values(0702, 'ABC', 'KUHF Transmitter Tower - ABC');
insert into BUILDING values(0703, 'KMJQ', 'KMJQ/KUHT Transmitter BUILDING');
insert into BUILDING values(0704, 'TMC', 'Texas Medical Center - 2151 Holcomb');
insert into BUILDING values(0705, 'NWC', 'Northwest Campus');
insert into BUILDING values(0706, 'FB1', 'Small Business Development Center - Rosenberg');
insert into BUILDING values(0707, 'FB2', 'Small Business Development Center - Missouri City');
insert into BUILDING values(0708, 'FB3', 'Small Business Development Center - Katy');
insert into BUILDING values(0709, 'BV', 'Small Business Development Center - Bryan');
insert into BUILDING values(0710, 'SBD', 'Small Business Development Center');
insert into BUILDING values(0711, 'ROCK', '4520 Rockwood - University Oak');
insert into BUILDING values(0713, 'CP1', 'Small Business Development Center - Bay City');
insert into BUILDING values(0714, 'TMC2', 'Texas Medical Center 2');
insert into BUILDING values(0715, 'NWC10', 'Northwest Campus 10');
insert into BUILDING values(0750, 'COCM', 'Coastal Center Caretaker Mobile Home');
insert into BUILDING values(0751, 'COEL', 'Coastal Center Environmental Laboratory');
insert into BUILDING values(0752, 'CORL', 'Coastal Center Research Laboratory');
insert into BUILDING values(0753, 'COES', 'Coastal Center Equipment Storage');
insert into BUILDING values(0756, 'COIT', 'Coastal Center Residential IT Equipment');
insert into BUILDING values(0757, 'GEOC', 'Geosciences Coastal Center IT Equipment');
insert into BUILDING values(0758, 'CROK', 'Coastal Center Rock Saw Facility');
insert into BUILDING values(0759, 'CRST', 'Coastal Center Rock Storage');
insert into BUILDING values(0760, 'CGRS', 'Coastal Center Greenhouse Service');
insert into BUILDING values(0762, 'WHI', 'West Houston Institute (UHS-Cinco Ranch)');
insert into BUILDING values(0800, 'CW', 'Campus Wide');
insert into BUILDING values(0801, 'LEP', 'Lynn Eusan Park');
insert into BUILDING values(0802, 'CFP', 'Cullen Family Plaza');
insert into BUILDING values(0803, 'BMG', 'Dr. Barnett Memorial Garden');
insert into BUILDING values(0804, 'CCG', 'The Cougar Community Garden');
insert into BUILDING values(0805, 'MP', 'Meditation Pond');
insert into BUILDING values(0806, 'LAG', 'Dena and Guy V. Lewis Azalea Garden');
insert into BUILDING values(0807, 'SRG', 'Margaret Sharpe Antique Rose Garden');
insert into BUILDING values(0808, 'TEN', 'Tennis Courts');
insert into BUILDING values(0809, 'SF', 'Soccer Field');
insert into BUILDING values(0820, 'SK4A', 'Security Kiosk Lot 4A');
insert into BUILDING values(0821, 'SK9C', 'Security Kiosk Lot 9C');
insert into BUILDING values(0822, 'SK12A', 'Security Kiosk Lot 12A');
insert into BUILDING values(0823, 'SK41', 'Security Kiosk Lot 41 (ERP)');
insert into BUILDING values(0824, 'SK42', 'Security Kiosk Lot 42 (ERP)');
insert into BUILDING values(0482, 'BPF', 'New Basketball Practice Facility');
insert into BUILDING values(0483, 'HBS2', 'Health and Biomedical Sciences Center 2');
insert into BUILDING values(0484, 'MREB', 'Multidisciplinary Research and Engineering BUILDING');


insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '1303', 1);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '1304', 1);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '1370', 1);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2361', 1);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1310', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1311', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1312', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1313', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1314', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1330', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1431', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1450', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '2311', 2);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '1309', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '1310', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '1320', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '1361', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '1362', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CHEM', '1301', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CHEM', '1331', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CHEM', '1332', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1302', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1330', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1340', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1350', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1360', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GEOL', '1376', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('NUTR', '2332', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHAR', '2362', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1301', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1302', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1305', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1306', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1321', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '1322', 3);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('AAS', '2320', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('AAS', '2330', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARAB', '3340', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARTH', '1300', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARTH', '1380', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CHNS', '3352', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CHNS', '3360', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3307', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3308', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3366', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3374', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2305', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2306', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2308', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2315', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2316', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2323', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2325', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2340', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3306', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3324', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3325', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3327', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3328', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3350', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3351', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '3360', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3318', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3319', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3321', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3322', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3362', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('FREN', '3364', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3350', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3362', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3364', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3369', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HISP', '2373', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HISP', '2374', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HISP', '2375', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2351', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2353', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2361', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2364', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2366', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2367', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2368', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2372', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2373', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3379', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3380', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '4330', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HON', '2301', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ILAS', '2350', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ILAS', '2360', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3305', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3306', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3308', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3335', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3336', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('JWST', '2380', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MAS', '3340', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHIL', '1301', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHIL', '1305', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3340', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3342', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '1301', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2310', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2311', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2330', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3331', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3373', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3374', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3375', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '2351', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '2352', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '2370', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '3351', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '3377', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WGSS', '2350', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WGSS', '2360', 4);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARCH', '2350', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARCH', '2351', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARTH', '1381', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3345', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '3381', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('DAN', '2307', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('DAN', '3310', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2307', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2318', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3363', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3381', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3384', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GERM', '3385', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '4371', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('INDS', '2355', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('INDS', '2356', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '2302', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '2342', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '2362', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '3300', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '3301', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHIL', '1361', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '2346', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3386', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('THEA', '1331', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('THEA', '1332', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '3366', 5);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '1376', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '1377', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '1378', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '1379', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2348', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2341', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '2343', 6); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '1107', 7); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '1336', 7); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '1337', 7); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '2336', 7); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '1300', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '2301', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '2302', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '2303', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CUST', '2300', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '2301', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '2304', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '2305', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HDFS', '2317', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HRMA', '2365', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('KIN', '1304', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3311', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3354', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '1300', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '2350', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '2351', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SOC', '1300', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SOC', '1301', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('TECH', '1313', 8); 
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('COSC', '1306', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CUIN', '2320', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '2370', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ELET', '2300', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1312', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1313', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1314', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1330', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1431', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1432', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1450', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '1451', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MATH', '2311', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHIL', '1321', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3312', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3316', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '3301', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('TMTH', '3360', 9);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '2304', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '3348', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '3361', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ANTH', '3381', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARAB', '3314', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARCH', '1359', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ARTH', '3312', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BCHS', '4311', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('BIOL', '3311', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '4305', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('CLAS', '4381', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('COMM', '1302', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('DAN', '2307', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('DAN', '3310', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '3344', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ECON', '3350', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGI', '2304', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ENGL', '2330', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('GENB', '4350', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HDCS', '1300', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HDFS', '1300', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3314', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3333', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3351', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '3369', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HIST', '4339', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HON', '3300', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('HRMA', '3358', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('IDNS', '4392', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3307', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '3309', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('ITAL', '4308', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('LAST', '3300', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '2301', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('MUSI', '3303', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHIL', '1334', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PHYS', '3313', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '2341', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('POLS', '3310', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '2344', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '3310', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('PSYC', '4321', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2340', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2350', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '2360', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '3370', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('RELS', '3381', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SOC', '3351', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('SPAN', '3384', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('TELS', '3363', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('THEA', '2344', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '3348', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '4351', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '4352', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '4356', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '4365', 10);
insert into CORE_CLASS(department_abbreviation, department_crn, core_id) values('WCL', '4367', 10);