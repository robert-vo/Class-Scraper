package com.scraper.main.pojo;

/**
 * Subject Enum contains the possible subjects for a given class.
 *
 * @author Robert Vo
 */
public enum Subject {
    AAS  ("African American Studies"),
    ACCT ("Accounting"),
    AFSC ("Air Force Science"),
    AMER ("American Cultures"),
    ANTH ("Anthropology"),
    ARAB ("Arabic"),
    ARAF ("Architect Affiliated St"),
    ARCH ("Architecture"),
    ARED ("Art Education"),
    ARLD ("Arts Leadership"),
    ARRE ("Arch Reciprocal Exch"),
    ART  ("Art"),
    ARTH ("Art History"),
    ASLI ("Amer Sign Lang Interpre"),
    ATP  ("Athletic Training Progr"),
    BCHS ("Biochemical & Biophys Sci"),
    BIOE ("Biomedical Engineering"),
    BIOL ("Biology"),
    BTEC ("Biotechnology"),
    BUAF ("Business Affiliated St"),
    BURE ("Business Reciprocal Exc"),
    BZAN ("Business Analytics"),
    CCS  ("Comparative Cult Studies"),
    CHEE ("Chemical Engineering"),
    CHEM ("Chemistry"),
    CHNS ("Chinese"),
    CIS  ("Computer Information Sys"),
    CIVE ("Civil & Environm Engr"),
    CLAS ("Classics"),
    CNST ("Construction Technology"),
    COMD ("Communication Disorders"),
    COMM ("Communication"),
    COOP ("Cooperative Engineering"),
    CORE ("Core Curriculum"),
    COSC ("Computer Science"),
    CUIN ("Curriculum & Instruction"),
    CUST ("Cultural & Urban Studies"),
    DAN  ("Dance"),
    DIGM ("Digital Media"),
    ECE  ("Electrical and Comp Engr"),
    ECON ("Economics"),
    EDAF ("Education Affil Studies"),
    EDRS ("Educational Research"),
    EDUC ("Education"),
    EGRP ("Engineering PROMES"),
    ELCS ("Educ Ldrshp & Cultural St"),
    ELED ("Elementary Education"),
    ELET ("Electrical-Electron Tec"),
    ENGI ("Engineering"),
    ENGL ("English"),
    ENRE ("Engineering Recipr Exc"),
    ENRG ("Energy & Sustainability"),
    ENTR ("Entrepreneurship"),
    EPSY ("Educational Psychology"),
    FINA ("Finance"),
    FORE ("Foresight"),
    FREN ("French"),
    GENB ("General Business Admin"),
    GEOL ("Geosciences"),
    GERM ("German"),
    GREK ("Greek"),
    GRET ("Global Retailing"),
    HDCS ("Hum Develop & Consum Sc"),
    HDFS ("Hum Develop & Fam Stds"),
    HIND ("Hindi"),
    HIST ("History"),
    HLT  ("Health Education"),
    HON  ("Honors College"),
    HRAF ("HRM Affiliated Studies"),
    HRD  ("Human Resources Developm"),
    HRMA ("Hotel & Restaurant Mgt"),
    HRRE ("HRM Reciprocal Exc"),
    IART ("Interdisciplinary Art"),
    IDNS ("Interdisciplinary-NSM"),
    IGS  ("International & Global St"),
    ILAS ("Interdisciplinary-LASS"),
    INAR ("Interior Arch"),
    INDE ("Industrial Engineering"),
    INDS ("Industrial Design"),
    INTB ("International Business"),
    IRW  ("Integ Reading & Writing"),
    ITAL ("Italian"),
    ITEC ("Information Syst Tech"),
    JPNS ("Japanese"),
    JWST ("Jewish Studies"),
    KIN  ("Kinesiology"),
    LAAF ("Law Affiliated Studies"),
    LARE ("Law Reciprocal Exchange"),
    LAST ("Latin Am Studies"),
    LATN ("Latin"),
    LAW  ("Law"),
    LSAF ("LASS Affiliated Studies"),
    LSRE ("LASS Reciprocal Exc"),
    MANA ("Management"),
    MARK ("Marketing and Entrepren"),
    MAS  ("Mexican Am Studies"),
    MATH ("Mathematics"),
    MECE ("Mechanical Engineering"),
    MECT ("Mechanical Technology"),
    MIS  ("Management Info. Systems"),
    MSCI ("Military Science"),
    MTLS ("Materials Engineering"),
    MUED ("Music Education"),
    MUSA ("Applied Music"),
    MUSI ("Music"),
    NAVY ("Navy"),
    NSAF ("NSM Affiliated Studies"),
    NSRE ("NSM Reciprocal Exchange"),
    NURS ("Nursing"),
    NUTR ("Nutrition"),
    OPTO ("Optometry"),
    PCEU ("Pharmaceutics"),
    PCOL ("Pharmacology"),
    PEB  ("Physical Edu Basic Instr"),
    PEP  ("Physical Ed Professional"),
    PETR ("Petroleum Engineering"),
    PHAR ("Pharmacy"),
    PHCA ("Pharmacy Practice"),
    PHIL ("Philosophy"),
    PHLA ("Pharm Leadership Admin"),
    PHLS ("Psych Hlth Learn Sci"),
    PHOP ("Physiological Optics"),
    PHYS ("Physics"),
    POLC ("Policy"),
    POLS ("Political Science"),
    PSYC ("Psychology"),
    PUBL ("Public Administration"),
    QSS  ("Quantitative Social Sci"),
    RELS ("Religious Studies"),
    RUSS ("Russian"),
    SAAF ("Affiliated Studies Grad"),
    SAER ("Systems Anal & Ed Res"),
    SARE ("Reciprocal Exchange Grad"),
    SCLT ("Supply Chain Logis Tech"),
    SCM  ("Supply Chain Mgmt"),
    SEDE ("Secondary Education"),
    SOC  ("Sociology"),
    SOCW ("Social Work"),
    SPAC ("Space Architecture"),
    SPAN ("Spanish"),
    STAT ("Statistics"),
    SUBS ("Subsea Engineering"),
    TEAF ("TECH Affiliated Studies"),
    TECH ("Technology"),
    TELS ("TECH Leadership& Superv"),
    TEPM ("Technology Project Mgt"),
    THEA ("Theatre"),
    TMTH ("Technical Mathematics"),
    UNIV ("University Studies"),
    VIET ("Vietnamese"),
    WCL  ("World Cultures & Lit"),
    WGSS ("WomenGendSexualitySt");

    protected final String fullSubjectName;

    Subject(String name) {
        fullSubjectName = name;
    }

    public String getFullSubjectName() {
        return fullSubjectName;
    }
}