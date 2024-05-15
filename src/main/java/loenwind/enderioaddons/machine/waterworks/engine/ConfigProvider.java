package loenwind.enderioaddons.machine.waterworks.engine;

import static loenwind.enderioaddons.common.NullHelper.notnull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.extended.NamedMapConverter;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;

import loenwind.enderioaddons.config.ConfigHandler;

public class ConfigProvider {

    private ConfigProvider() {}

    @Nonnull
    public static XStream makeXStream() {
        XStream xstream = new XStream();
        if (ConfigProvider.class.getClassLoader() != null) {
            xstream.setClassLoader(ConfigProvider.class.getClassLoader());
        }

        xstream.registerLocalConverter(
            Stash.class,
            "contents",
            new NamedMapConverter(
                xstream.getMapper(),
                "component",
                "name",
                String.class,
                "ppm",
                Double.class,
                true,
                true,
                xstream.getConverterLookup()));
        xstream.addImplicitCollection(Material.class, "components");
        xstream.useAttributeFor(Material.class, "name");
        xstream.useAttributeFor(Material.class, "prio");
        xstream.useAttributeFor(Material.class, "volume");
        xstream.useAttributeFor(Material.class, "density");
        xstream.useAttributeFor(Component.class, "name");
        xstream.useAttributeFor(Component.class, "factor");
        xstream.useAttributeFor(Component.class, "count");
        xstream.useAttributeFor(Component.class, "granularity");
        xstream.useAttributeFor(MinecraftItem.class, "modID");
        xstream.useAttributeFor(MinecraftItem.class, "itemName");
        xstream.useAttributeFor(MinecraftItem.class, "itemMeta");
        xstream.useAttributeFor(OreDictionaryItem.class, "oreDictionary");
        xstream.alias("water", Water.class);
        xstream.alias("material", Material.class);
        xstream.alias("component", Component.class);
        xstream.alias("OreDictionaryItem", OreDictionaryItem.class);
        xstream.alias("MinecraftItem", MinecraftItem.class);

        ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
        mapper.addClassAlias("amount", Double.class);
        xstream.registerLocalConverter(Component.class, "granularities", new CollectionConverter(mapper));
        return xstream;
    }

    private static Object readConfig(@Nonnull XStream xstream, @Nonnull String fileName) throws IOException {
        File configFile = new File(ConfigHandler.configDirectory, fileName);

        if (configFile.exists()) {
            return xstream.fromXML(configFile);
        }

        InputStream defaultFile = ConfigProvider.class.getResourceAsStream("/assets/enderioaddons/config/" + fileName);
        if (defaultFile == null) {
            throw new IOException(
                "Could not get resource /assets/enderioaddons/config/" + fileName + " from classpath. ");
        }

        Object myObject = xstream.fromXML(defaultFile);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(configFile, false));
            xstream.toXML(myObject, writer);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return myObject;
    }

    @Nonnull
    public static Water readConfig() {
        XStream xstream = makeXStream();
        Water result;

        try {
            result = (Water) readConfig(xstream, "water.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return notnull(result, "Invalid Water configuration, it seems to be empty?");
    }
}
