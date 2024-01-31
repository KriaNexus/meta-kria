FILESEXTRAPATHS:prepend:kria := "${THISDIR}/k26-som:"

SRCREV_FORMAT:kria = "device-tree"
DT_UBOOT_BRANCH ?= "master"
DT_UBOOT_SRCREV ?= "00bd60bf78b370b6b3dfe671bc75c37cbb1de3ef"
SRC_URI:append:kria = " git://github.com/Xilinx/u-boot-xlnx.git;protocol=https;branch=${DT_UBOOT_BRANCH};destsuffix=u-boot-xlnx;name=uboot"
SRCREV_uboot = "${DT_UBOOT_SRCREV}"

UBOOT_DTFILES_BUNDLE:kria ?= "1"
UBOOT_DTFILE_PREFIX:kria ?= "SMK"

do_configure:append:kria() {
    for dts in ${UBOOT_DT_FILES}; do
        cp ${WORKDIR}/u-boot-xlnx/arch/arm/dts/${dts} ${DT_FILES_PATH}
    done

    printf "* ${PN}\nSRCREV: ${SRCREV}\nBRANCH: ${BRANCH}\n" > ${S}/device-tree-${MACHINE}.manifest
    printf "** ${PN} - u-boot-xlnx\nDT_UBOOT_SRCREV: ${DT_UBOOT_SRCREV}\nDT_UBOOT_BRANCH: ${DT_UBOOT_BRANCH}\n\n" >> ${S}/device-tree-${MACHINE}.manifest
}

EXTRA_OVERLAYS:append:kria = " system.dtsi"

YAML_CONSOLE_DEVICE_CONFIG:kria = "psu_uart_1"
YAML_MAIN_MEMORY_CONFIG:kria = "PSU_DDR_0"
YAML_ENABLE_NO_ALIAS:kria = "1"

DT_PADDING_SIZE:kria = "0x1000"
DTC_FLAGS:kria += "-@"

do_install:append:kria() {
    # Remove dtbo files, these are no usable
    # keep pl.dtbo
    rm -f ${D}/boot/devicetree/zynqmp-sck*.dtbo
}

do_deploy:append:kria() {
    install -m 0644 ${S}/device-tree-${MACHINE}.manifest ${DEPLOYDIR}/
}
