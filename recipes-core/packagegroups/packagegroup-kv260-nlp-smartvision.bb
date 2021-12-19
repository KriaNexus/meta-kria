DESCRIPTION = "nlp-smartvision related Packages"

inherit packagegroup

NLP_SMARTVISION_PACKAGES = " \
	ap1302-ar1335-single-firmware \
	kv260-nlp-smartvision \
	kv260-nlp-smartvision-models \
	nlp-smartvision \
	"

RDEPENDS:${PN} = "${NLP_SMARTVISION_PACKAGES}"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:k26-kv = "${MACHINE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

