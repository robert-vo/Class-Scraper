use class;

DROP TABLE IF EXISTS DEPARTMENT;
DROP TABLE IF EXISTS CLASS;
DROP TABLE IF EXISTS TERMS;

CREATE TABLE DEPARTMENT (
	Department_Abbreviation	VARCHAR(5) PRIMARY KEY,
    Department_Name			varchar(75) NOT NULL
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
    Name                VARCHAR(100)    NOT NULL,
    Status              ENUM('Open', 'Closed') ,
    ATTRIBUTES          VARCHAR(100),
    DATES               VARCHAR(100),
    DAYS_TIMES          VARCHAR(100),
    INSTRUCTOR          VARCHAR(100),
    INSTRUCTOR_EMAIL    VARCHAR(100),
    LOCATION            VARCHAR(100),
    ROOM                VARCHAR(100),
    FORMAT              VARCHAR(100),
    DESCRIPTION         VARCHAR(500),
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
    ONLINE_COURSE		BOOLEAN DEFAULT FALSE,
    foreign key (Term_ID)
		REFERENCES TERMS(Term_ID)
        ON DELETE no action
);

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
