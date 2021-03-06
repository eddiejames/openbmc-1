require recipes-graphics/xorg-app/xorg-app-common.inc
SUMMARY = "xterm is the standard terminal emulator for the X Window System"
DEPENDS = "libxaw xorgproto libxext libxau libxinerama libxpm ncurses"

LIC_FILES_CHKSUM = "file://xterm.h;beginline=3;endline=31;md5=c7faceb872d90115e7c0ad90e90c390d"

SRC_URI = "http://invisible-mirror.net/archives/${BPN}/${BP}.tgz"

SRC_URI[md5sum] = "05340ef6eabb96826953346c64b35225"
SRC_URI[sha256sum] = "8b1f5121dbf3690de9d85a3b9329e3357021705dd7c21da192e47542769275be"
PACKAGECONFIG ?= ""
PACKAGECONFIG[xft] = "--enable-freetype,--disable-freetype,libxft fontconfig freetype-native"

EXTRA_OECONF = " --x-includes=${STAGING_INCDIR} \
                 --x-libraries=${STAGING_LIBDIR} \
                 FREETYPE_CONFIG=${STAGING_BINDIR_CROSS}/freetype-config \
                 --disable-imake \
                 --disable-rpath-hack \
                 --disable-setuid \
                 --with-app-defaults=${datadir}/X11/app-defaults \
                 "

B = "${S}"

do_configure() {
    gnu-configize --force
    sed -e "s%/usr/contrib/X11R6%${STAGING_LIBDIR}%g" -i configure
    oe_runconf
}

# busybox can supply resize too
inherit update-alternatives

ALTERNATIVE_${PN} = "resize"
