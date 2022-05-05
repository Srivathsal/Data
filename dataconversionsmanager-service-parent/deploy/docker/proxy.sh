echo "Setting the HTTP Proxy for IHP only - either QDC or LVDC"

if [[ "$CFG_DC" == "qdc" ]]; then
	if [[ ${CFG_ENV} == "prd" ]]; then
		export PROXY=qy1prdproxy01.ie.intuit.net
	else
		export PROXY=qy1prdproxy01.pprod.ie.intuit.net
	fi
elif [[ "$CFG_DC" == "lvdc" ]]; then
	if [[ ${CFG_ENV} == "prd" ]]; then
		export PROXY=lvdcproxy01.ie.intuit.net
	else
		export PROXY=lvdcproxy01.pprod.ie.intuit.net
	fi
fi