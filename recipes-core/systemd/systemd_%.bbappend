# Prevent boot delay on devices with multiple network interfaces
do_install:append:k26() {
    sed -i '/ExecStart/ s/$/ --any/' ${D}${systemd_system_unitdir}/systemd-networkd-wait-online.service
}