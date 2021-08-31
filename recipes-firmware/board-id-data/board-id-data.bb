SUMMARY = "Xilinx EEPROM blobs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e02145ed27d5ba38507cf0f17e27adf0"

BRANCH ?= "release-2021.2"
SRC_URI = "git://github.com/Xilinx/xlnx-board-id-data.git;protocol=https;branch=${BRANCH}"
SRCREV ?= "960fa76a8f682f7d66e811aaa76eff2e006e873d"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^$"

# Specify a default in case boardvariant isn't available
BOARDVARIANT_ARCH ??= "${MACHINE_ARCH}"
PACKAGE_ARCH = "${BOARDVARIANT_ARCH}"

require board-id-k26.inc

inherit deploy

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"

# Don't generate packages, this only gets deployed
PACKAGES = ""

# Each board should provide the SOM_EEPROM and CC_EEPROM files
PATH_EEPROM ?= "none"
SOM_EEPROM ?= "none"
CC_EEPROM ?= "none"

DEPLOY_SUFFIX = "${PKGE}-${PKGV}-${PKGR}-${MACHINE}${IMAGE_VERSION_SUFFIX}"

do_deploy() {
	if [ -e ${S}/${PATH_EEPROM}/${SOM_EEPROM} ]; then
		install -Dm 0644 ${S}/${PATH_EEPROM}/${SOM_EEPROM} ${DEPLOYDIR}/som-eeprom-${DEPLOY_SUFFIX}.bin
		ln -sf som-eeprom-${DEPLOY_SUFFIX}.bin ${DEPLOYDIR}/som-eeprom.bin
	else
		bbwarn "No SOM eeprom found."
	fi
	if [ -e ${S}/${PATH_EEPROM}/${CC_EEPROM} ]; then
		install -Dm 0644 ${S}/${PATH_EEPROM}/${CC_EEPROM} ${DEPLOYDIR}/cc-eeprom-${DEPLOY_SUFFIX}.bin
		ln -sf cc-eeprom-${DEPLOY_SUFFIX}.bin ${DEPLOYDIR}/cc-eeprom.bin
	else
		bbwarn "No CC eeprom found."
	fi
}

addtask deploy before do_build after do_install
