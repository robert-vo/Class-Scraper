	use class;

DROP TABLE IF EXISTS CLASS;
DROP TABLE IF EXISTS TERMS;
DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS BUILDING;

CREATE TABLE DEPARTMENT (
	Department_Abbreviation	VARCHAR(5) PRIMARY KEY,
    Department_Name			varchar(75) NOT NULL
);

CREATE TABLE BUILDING (
	Building_ID				INT,
	Building_Abbreviation	VARCHAR(5) PRIMARY KEY,
    Building_Name			VARCHAR(100) NOT NULL
);

CREATE TABLE TERMS (
	Term_ID		INT PRIMARY KEY,
    Year		INT NOT NULL,
    Semester	ENUM('Fall', 'Summer', 'Spring')
);

CREATE TABLE CLASS (
	Class_ID            INT     PRIMARY KEY AUTO_INCREMENT,
    Term_ID             INT,
	Title               VARCHAR(100),
    CRN                 INT     ,
    Department          VARCHAR(5)    NOT NULL,
    Department_CRN      VARCHAR(5)    NOT NULL,
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
    DESCRIPTION         VARCHAR(1000),
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
    LAST_UPDATED_AT			TIMESTAMP default current_timestamp 
		on update current_timestamp,
    foreign key (Term_ID)
		REFERENCES TERMS(Term_ID)
        ON DELETE no action,
    foreign key (Department)
        REFERENCES DEPARTMENT(Department_Abbreviation)
        ON DELETE no action,
	foreign key (Building_Abbv)
		REFERENCES BUILDING(Building_Abbreviation)
        ON DELETE no action
);

insert into terms values(1950, 2015, 'Spring');
insert into terms values(1960, 2015, 'Summer');
insert into terms values(1970, 2015, 'Fall');
insert into terms values(1980, 2016, 'Spring');
insert into terms values(1990, 2016, 'Summer');
insert into terms values(2000, 2016, 'Fall');
insert into terms values(2010, 2017, 'Spring');
insert into terms values(2020, 2017, 'Summer');
insert into terms values(2030, 2017, 'Fall');
insert into terms values(2040, 2018, 'Spring');
insert into terms values(2050, 2018, 'Summer');
insert into terms values(2060, 2018, 'Fall');
insert into terms values(2070, 2019, 'Spring');
insert into terms values(2080, 2019, 'Summer');
insert into terms values(2090, 2019, 'Fall');
insert into terms values(2100, 2020, 'Spring');
insert into terms values(2110, 2020, 'Summer');
insert into terms values(2120, 2020, 'Fall');

insert into department values("AAS","African American Studies");
insert into department values("ACCT","Accounting");
insert into department values("AFSC","Air Force Science");
insert into department values("AMER","American Cultures");
insert into department values("ANTH","Anthropology");
insert into department values("ARAB","Arabic");
insert into department values("ARAF","Architect Affiliated St");
insert into department values("ARCH","Architecture");
insert into department values("ARED","Art Education");
insert into department values("ARLD","Arts Leadership");
insert into department values("ARRE","Arch Reciprocal Exch");
insert into department values("ART","Art");
insert into department values("ARTH","Art History");
insert into department values("ASLI","Amer Sign Lang Interpre");
insert into department values("ATP","Athletic Training Progr");
insert into department values("BCHS","Biochemical & Biophys Sci");
insert into department values("BIOE","Biomedical Engineering");
insert into department values("BIOL","Biology");
insert into department values("BTEC","Biotechnology");
insert into department values("BUAF","Business Affiliated St");
insert into department values("BURE","Business Reciprocal Exc");
insert into department values("BZAN","Business Analytics");
insert into department values("CCS","Comparative Cult Studies");
insert into department values("CHEE","Chemical Engineering");
insert into department values("CHEM","Chemistry");
insert into department values("CHNS","Chinese");
insert into department values("CIS","Computer Information Sys");
insert into department values("CIVE","Civil & Environm Engr");
insert into department values("CLAS","Classics");
insert into department values("CNST","Construction Technology");
insert into department values("COMD","Communication Disorders");
insert into department values("COMM","Communication");
insert into department values("COOP","Cooperative Engineering");
insert into department values("CORE","Core Curriculum");
insert into department values("COSC","Computer Science");
insert into department values("CUIN","Curriculum & Instruction");
insert into department values("CUST","Cultural & Urban Studies");
insert into department values("DAN","Dance");
insert into department values("DIGM","Digital Media");
insert into department values("ECE","Electrical and Comp Engr");
insert into department values("ECON","Economics");
insert into department values("EDAF","Education Affil Studies");
insert into department values("EDRS","Educational Research");
insert into department values("EDUC","Education");
insert into department values("EGRP","Engineering PROMES");
insert into department values("ELCS","Educ Ldrshp & Cultural St");
insert into department values("ELED","Elementary Education");
insert into department values("ELET","Electrical-Electron Tec");
insert into department values("ENGI","Engineering");
insert into department values("ENGL","English");
insert into department values("ENRE","Engineering Recipr Exc");
insert into department values("ENRG","Energy & Sustainability");
insert into department values("ENTR","Entrepreneurship");
insert into department values("EPSY","Educational Psychology");
insert into department values("FINA","Finance");
insert into department values("FORE","Foresight");
insert into department values("FREN","French");
insert into department values("GENB","General Business Admin");
insert into department values("GEOL","Geosciences");
insert into department values("GERM","German");
insert into department values("GIS" ,"Global and International Studies");
insert into department values("GREK","Greek");
insert into department values("GRET","Global Retailing");
insert into department values("HDCS","Hum Develop & Consum Sc");
insert into department values("HDFS","Hum Develop & Fam Stds");
insert into department values("HIND","Hindi");
insert into department values("HIST","History");
insert into department values("HLT","Health Education");
insert into department values("HON","Honors College");
insert into department values("HRAF","HRM Affiliated Studies");
insert into department values("HRD","Human Resources Developm");
insert into department values("HRMA","Hotel & Restaurant Mgt");
insert into department values("HRRE","HRM Reciprocal Exc");
insert into department values("IART","Interdisciplinary Art");
insert into department values("IDNS","Interdisciplinary-NSM");
insert into department values("IGS","International & Global St");
insert into department values("ILAS","Interdisciplinary-LASS");
insert into department values("INAR","Interior Arch");
insert into department values("INDE","Industrial Engineering");
insert into department values("INDS","Industrial Design");
insert into department values("INTB","International Business");
insert into department values("IRW","Integ Reading & Writing");
insert into department values("ITAL","Italian");
insert into department values("ITEC","Information Syst Tech");
insert into department values("JPNS","Japanese");
insert into department values("JWST","Jewish Studies");
insert into department values("KIN","Kinesiology");
insert into department values("LAAF","Law Affiliated Studies");
insert into department values("LARE","Law Reciprocal Exchange");
insert into department values("LAST","Latin Am Studies");
insert into department values("LATN","Latin");
insert into department values("LAW","Law");
insert into department values("LSAF","LASS Affiliated Studies");
insert into department values("LSRE","LASS Reciprocal Exc");
insert into department values("MANA","Management");
insert into department values("MARK","Marketing and Entrepren");
insert into department values("MAS","Mexican Am Studies");
insert into department values("MATH","Mathematics");
insert into department values("MECE","Mechanical Engineering");
insert into department values("MECT","Mechanical Technology");
insert into department values("MIS","Management Info. Systems");
insert into department values("MSCI","Military Science");
insert into department values("MTLS","Materials Engineering");
insert into department values("MUED","Music Education");
insert into department values("MUSA","Applied Music");
insert into department values("MUSI","Music");
insert into department values("NAVY","Navy");
insert into department values("NSAF","NSM Affiliated Studies");
insert into department values("NSRE","NSM Reciprocal Exchange");
insert into department values("NURS","Nursing");
insert into department values("NUTR","Nutrition");
insert into department values("OPTO","Optometry");
insert into department values("PCEU","Pharmaceutics");
insert into department values("PCOL","Pharmacology");
insert into department values("PEB","Physical Edu Basic Instr");
insert into department values("PEP","Physical Ed Professional");
insert into department values("PETR","Petroleum Engineering");
insert into department values("PHAR","Pharmacy");
insert into department values("PHCA","Pharmacy Practice");
insert into department values("PHIL","Philosophy");
insert into department values("PHLA","Pharm Leadership Admin");
insert into department values("PHLS","Psych Hlth Learn Sci");
insert into department values("PHOP","Physiological Optics");
insert into department values("PHYS","Physics");
insert into department values("POLC","Policy");
insert into department values("POLS","Political Science");
insert into department values("PSYC","Psychology");
insert into department values("PUBL","Public Administration");
insert into department values("QSS","Quantitative Social Sci");
insert into department values("RELS","Religious Studies");
insert into department values("RUSS","Russian");
insert into department values("SAAF","Affiliated Studies Grad");
insert into department values("SAER","Systems Anal & Ed Res");
insert into department values("SARE","Reciprocal Exchange Grad");
insert into department values("SCLT","Supply Chain Logis Tech");
insert into department values("SCM","Supply Chain Mgmt");
insert into department values("SEDE","Secondary Education");
insert into department values("SOC","Sociology");
insert into department values("SOCW","Social Work");
insert into department values("SPAC","Space Architecture");
insert into department values("SPAN","Spanish");
insert into department values("STAT","Statistics");
insert into department values("SUBS","Subsea Engineering");
insert into department values("TEAF","TECH Affiliated Studies");
insert into department values("TECH","Technology");
insert into department values("TELS","TECH Leadership& Superv");
insert into department values("TEPM","Technology Project Mgt");
insert into department values("THEA","Theatre");
insert into department values("TMTH","Technical Mathematics");
insert into department values("UNIV","University Studies");
insert into department values("VIET","Vietnamese");
insert into department values("WCL","World Cultures & Lit");
insert into department values("WGSS","WomenGendSexualitySt");

insert into building values(0000, '', '');
insert into building values(0101, 'CRW', 'Chancellor\'s Residence Wortham House');
insert into building values(0104, 'P2', 'KUHT Fiber Optics Building');
insert into building values(0105, 'TVTE', 'KUHT Telephone Equipment');
insert into building values(0106, 'TV', 'Texas Learning & Computational Center Annex');
insert into building values(0108, 'P1', 'Cullen Annex Laboratory');
insert into building values(0111, 'DYN', 'Dynamometer Test Laboratory');
insert into building values(0115, 'OFGR', 'Office of Governmental Relations');
insert into building values(0116, 'ACT', 'Safety, Human Factors & Ergonomic Laboratory');
insert into building values(0117, 'TVFB', 'KUHT TV at Fort Bend - Tower');
insert into building values(0118, 'WRCH', 'Wortham Residence Coach House');
insert into building values(0119, 'ACTA', 'Texas Manufacturing Assistance Center');
insert into building values(0120, 'UHFB', 'Albert and Mamie George Building');
insert into building values(0121, 'FBA2', 'Brazos Hall');
insert into building values(0122, 'UBL', 'University Branch Library (UHS-Sugar Land)');
insert into building values(0125, 'WHS', 'Wortham House Storage');
insert into building values(0126, 'SA1', 'Sugar Land Annex 1');
insert into building values(0127, 'SA2', 'Sugar Land Annex 2');
insert into building values(0128, 'SA3', 'Sugar Land Annex 3');
insert into building values(0199, 'CRSA', 'Clinical Research Services Annex');
insert into building values(0400, 'CSS', 'Cougar Sub-Station');
insert into building values(0401, 'ERP1', 'Energy Research Park 01');
insert into building values(0402, 'ERP2', 'Energy Research Park 02');
insert into building values(0403, 'ERP3', 'Energy Research Park 03');
insert into building values(0404, 'ERP4', 'Conference & Research Building');
insert into building values(0405, 'ERP5', 'Energy Research Park 05');
insert into building values(0406, 'ERP6', 'Energy Research Park 06');
insert into building values(0407, 'ERP7', 'Energy Research Park 07');
insert into building values(0408, 'ERP8', 'Energy Research Park 08');
insert into building values(0409, 'ERP9', 'ConocoPhillips Petroleum Engineering Building');
insert into building values(0410, 'ERP10', 'Energy Research Park 10');
insert into building values(0411, 'ERP11', 'Energy Research Park 11');
insert into building values(0413, 'ERP13', 'Energy Research Park 13');
insert into building values(0414, 'ERP14', 'Energy Research Park 14');
insert into building values(0415, 'ERP15', 'Energy Device Fabrication Laboratory');
insert into building values(0419, 'ERPA', 'Energy Research Park Annex');
insert into building values(0420, 'ERP20', 'Energy Research Park Storage');
insert into building values(0485, 'STAD', 'TDECU Stadium');
insert into building values(0486, 'ATL', 'Atmospheric Testing Laboratory');
insert into building values(0487, 'UCN', 'Student Center North');
insert into building values(0488, 'SCP1', 'Central Plant Satellite');
insert into building values(0489, 'KUHA2', 'KUHA - Renters Building');
insert into building values(0490, 'WCSG', 'Welcome Center Student Garage');
insert into building values(0491, 'KUHA', 'KUHA - Transmitter Building');
insert into building values(0492, 'SST', 'Cougar Softball Stadium Ticket Booth');
insert into building values(0493, 'SS', 'Cougar Softball Stadium');
insert into building values(0494, 'AAA', 'Agnes Arnold Auditorium');
insert into building values(0495, 'CPH', 'Cougar Place');
insert into building values(0496, 'CV2', 'Cougar Village 2');
insert into building values(0498, 'DTF', 'Bulk Fueling Diesel Tank Farm');
insert into building values(0499, 'CBB', 'Classroom and Business Building');
insert into building values(0500, 'BOA', 'Bayou Oaks Apartments');
insert into building values(0501, 'C', 'Roy G. Cullen');
insert into building values(0502, 'S', 'Science Building');
insert into building values(0503, 'T', 'Technology Annex');
insert into building values(0504, 'CCC', 'Child Care Center');
insert into building values(0505, 'JDA', 'J. Davis Armistead');
insert into building values(0506, 'COM', 'Jack J. Valenti School of Communication');
insert into building values(0507, 'WT', 'Cynthia Woods Mitchell Center for the Arts/Wortham Theatre');
insert into building values(0508, 'T2', 'College of Technology Building');
insert into building values(0509, 'L', 'M. D. Anderson Library');
insert into building values(0513, 'INF2', 'Visitor Information Booth 2');
insert into building values(0514, 'EPS2', 'Fire & Life Safety - Storage');
insert into building values(0515, 'PP', 'Central Power Plant');
insert into building values(0516, 'E', 'Ezekiel W. Cullen');
insert into building values(0517, 'A', 'Cullen Performance Hall');
insert into building values(0518, 'CLA', 'Calhoun Lofts Apartments');
insert into building values(0519, 'UHPD', 'Department of Public Safety - UH Police');
insert into building values(0520, 'MSM', 'Rebecca & John J. Moores School of Music');
insert into building values(0521, 'GS', 'Grounds Storage Building');
insert into building values(0522, 'CRWC', 'Campus Recreation & Wellness Center');
insert into building values(0523, 'AGL', 'Science & Engineering Annex');
insert into building values(0524, 'SSC', 'Student Service Center 1');
insert into building values(0525, 'HC', 'Health Center');
insert into building values(0526, 'SS2', 'Student Service Center 2');
insert into building values(0527, 'EPS3', 'EHRM1');
insert into building values(0528, 'MH', 'LeRoy & Lucile Melcher Hall');
insert into building values(0529, 'SEC', 'Science & Engineering Classroom Building');
insert into building values(0531, 'HP', 'Hofheinz Pavilion');
insert into building values(0532, 'GAR', 'Susanna Garrison Gymnasium');
insert into building values(0533, 'MEL', 'Melcher Gymnasium/Charter School');
insert into building values(0534, 'H', 'Fred J. Heyne');
insert into building values(0535, 'EPS1', 'EHRM2');
insert into building values(0536, 'CPB', 'LeRoy & Lucile Melcher Center for Public Broadcasting');
insert into building values(0537, 'BL', 'Bates Law');
insert into building values(0538, 'TU2', 'Teaching Unit 2 Building');
insert into building values(0539, 'KH', 'Max Krost Hall');
insert into building values(0540, 'LL', 'John M. O\'Quinn Law Library');
insert into building values(0541, 'UTS', 'Utility Tunnel System');
insert into building values(0542, 'SPA', 'South Park Annex');
insert into building values(0543, 'ARC', 'Gerald D. Hines College of Architecture');
insert into building values(0544, 'CEMO', 'Michael J. Cemo Hall');
insert into building values(0545, 'SERC', 'Science & Engineering Research Center');
insert into building values(0546, 'EPG', 'East Parking Garage');
insert into building values(0547, 'PGH', 'Philip Guthrie Hoffman Hall');
insert into building values(0548, 'AMB', 'Athletics Maintenance Building');
insert into building values(0549, 'SW', 'Graduate College of Social Work');
insert into building values(0550, 'SR', 'Science and Research 1');
insert into building values(0551, 'SR2', 'Science and Research 2');
insert into building values(0552, 'BKD', 'Burdette Keeland Jr. Design & Exploration Center');
insert into building values(0553, 'WC', 'Welcome Center & Parking Garage');
insert into building values(0555, 'LH', 'Law Residence Hall');
insert into building values(0556, 'SH', 'Settegast Residence Hall');
insert into building values(0557, 'BH', 'Bates Residence Hall');
insert into building values(0558, 'TH', 'Taub Residence Hall');
insert into building values(0559, 'OB', 'E. E. Oberholtzer Residence Hall');
insert into building values(0560, 'UPD', 'UH-DPS Parking Enforcement');
insert into building values(0561, 'CRWA', 'CRWC Annex');
insert into building values(0562, 'ADB', 'A. D. Bruce Religion Center');
insert into building values(0563, 'CV', 'Cougar Village');
insert into building values(0564, 'F', 'Lamar Fleming, Jr.');
insert into building values(0565, 'UC', 'Student Center South');
insert into building values(0567, 'UCS', 'Student Center Satellite');
insert into building values(0568, 'CSD', 'Justin Dart Jr. Center for Students with DisABILITIES');
insert into building values(0569, 'CULLO', 'Cullen Oaks Apartments');
insert into building values(0570, 'BATC', 'Athletics Batting Cage');
insert into building values(0572, 'GSS', 'General Services Storage Building');
insert into building values(0573, 'ALUM', 'Alumni Center');
insert into building values(0574, 'ATH2', 'Athletic Center');
insert into building values(0575, 'CO', 'Cambridge Oaks Apartments');
insert into building values(0576, 'STL', 'Science Teaching Laboratory Building');
insert into building values(0578, 'AH', 'Agnes Arnold Hall');
insert into building values(0579, 'D', 'Cullen College of Engineering 1');
insert into building values(0580, 'D2', 'Engineering Lecture Hall');
insert into building values(0581, 'D3', 'Cullen College of Engineering 2');
insert into building values(0582, 'BF', 'Cougar Baseball Field');
insert into building values(0583, 'BFT', 'Cougar Baseball Field Ticket Booth');
insert into building values(0584, 'MR', 'Moody Towers Residence Halls');
insert into building values(0585, 'GEN', 'General Services Building');
insert into building values(0586, 'CAM', 'Isabel C. Cameron');
insert into building values(0587, 'FH', 'Stephen Power Farish Hall');
insert into building values(0588, 'M', 'Charles F. McElhinney Hall');
insert into building values(0589, 'FA', 'Fine Arts Building');
insert into building values(0590, 'CHC', 'Conrad Hilton College of Hotel & Restaurant Management');
insert into building values(0591, 'INF', 'Visitor Information Booth 1');
insert into building values(0592, 'HBS', 'Health and Biomedical Sciences Center');
insert into building values(0593, 'HSC', 'University of Houston Science Center');
insert into building values(0594, 'EERC', 'Engineering Education Resource Center');
insert into building values(0595, 'RES', 'Chinese Star Restaurant');
insert into building values(0596, 'CC', 'Computing Center');
insert into building values(0597, 'SPG', 'Stadium Parking Garage');
insert into building values(0598, 'CRS', 'Clinical Research Services Center');
insert into building values(0599, 'CLTF', 'Carl Lewis International Track & Field Complex');
insert into building values(0701, 'PHA', 'College of Pharmacy Texas Medical Center');
insert into building values(0702, 'ABC', 'KUHF Transmitter Tower - ABC');
insert into building values(0703, 'KMJQ', 'KMJQ/KUHT Transmitter Building');
insert into building values(0704, 'TMC', 'Texas Medical Center - 2151 Holcomb');
insert into building values(0705, 'NWC', 'Northwest Campus');
insert into building values(0706, 'FB1', 'Small Business Development Center - Rosenberg');
insert into building values(0707, 'FB2', 'Small Business Development Center - Missouri City');
insert into building values(0708, 'FB3', 'Small Business Development Center - Katy');
insert into building values(0709, 'BV', 'Small Business Development Center - Bryan');
insert into building values(0710, 'SBD', 'Small Business Development Center');
insert into building values(0711, 'ROCK', '4520 Rockwood - University Oak');
insert into building values(0713, 'CP1', 'Small Business Development Center - Bay City');
insert into building values(0714, 'TMC2', 'Texas Medical Center 2');
insert into building values(0715, 'NWC10', 'Northwest Campus 10');
insert into building values(0750, 'COCM', 'Coastal Center Caretaker Mobile Home');
insert into building values(0751, 'COEL', 'Coastal Center Environmental Laboratory');
insert into building values(0752, 'CORL', 'Coastal Center Research Laboratory');
insert into building values(0753, 'COES', 'Coastal Center Equipment Storage');
insert into building values(0756, 'COIT', 'Coastal Center Residential IT Equipment');
insert into building values(0757, 'GEOC', 'Geosciences Coastal Center IT Equipment');
insert into building values(0758, 'CROK', 'Coastal Center Rock Saw Facility');
insert into building values(0759, 'CRST', 'Coastal Center Rock Storage');
insert into building values(0760, 'CGRS', 'Coastal Center Greenhouse Service');
insert into building values(0762, 'WHI', 'West Houston Institute (UHS-Cinco Ranch)');
insert into building values(0800, 'CW', 'Campus Wide');
insert into building values(0801, 'LEP', 'Lynn Eusan Park');
insert into building values(0802, 'CFP', 'Cullen Family Plaza');
insert into building values(0803, 'BMG', 'Dr. Barnett Memorial Garden');
insert into building values(0804, 'CCG', 'The Cougar Community Garden');
insert into building values(0805, 'MP', 'Meditation Pond');
insert into building values(0806, 'LAG', 'Dena and Guy V. Lewis Azalea Garden');
insert into building values(0807, 'SRG', 'Margaret Sharpe Antique Rose Garden');
insert into building values(0808, 'TEN', 'Tennis Courts');
insert into building values(0809, 'SF', 'Soccer Field');
insert into building values(0820, 'SK4A', 'Security Kiosk Lot 4A');
insert into building values(0821, 'SK9C', 'Security Kiosk Lot 9C');
insert into building values(0822, 'SK12A', 'Security Kiosk Lot 12A');
insert into building values(0823, 'SK41', 'Security Kiosk Lot 41 (ERP)');
insert into building values(0824, 'SK42', 'Security Kiosk Lot 42 (ERP)');
insert into building values(0482, 'BPF', 'New Basketball Practice Facility');
insert into building values(0483, 'HBS2', 'Health and Biomedical Sciences Center 2');
insert into building values(0484, 'MREB', 'Multidisciplinary Research and Engineering Building');
