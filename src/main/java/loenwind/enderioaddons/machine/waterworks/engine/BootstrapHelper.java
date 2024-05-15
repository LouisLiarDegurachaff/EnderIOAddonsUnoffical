package loenwind.enderioaddons.machine.waterworks.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

public class BootstrapHelper {

    @Nonnull
    private static String dumpPath = ".";

    public static void main(String[] args) throws IOException {
        XStream xstream = ConfigProvider.makeXStream();

        createDummyConfigFile(xstream);
    }

    public static void dumpConfig() {
        XStream xstream = ConfigProvider.makeXStream();
        try {
            dumpConfig(xstream, new Engine(ConfigProvider.readConfig()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDummyConfigFile(@Nonnull XStream xstream) throws IOException {
        Water wc = new Water();

        wc.getContents()
            .put("Chloride", 18980.0);
        wc.getContents()
            .put("Sodium", 10561.0);
        wc.getContents()
            .put("Aluminium", 0.001);

        Material m = new Material("Aluminium", 1, new OreDictionaryItem("blockAluminium"), 1000000.0, 2.70);

        m.getComponents()
            .add(new Component("Aluminium", 1.0, 1.0, 1));

        wc.getMaterials()
            .add(m);

        m = new Material("Salt", 1, new MinecraftItem("harvestcraft", "foodSalt", 0), 1000000.0, 2.165);

        m.getComponents()
            .add(new Component("Chloride", 1000.0, 1.0, 1));
        m.getComponents()
            .add(new Component("Sodium", 1000.0, 0.01, 1));

        wc.getMaterials()
            .add(m);

        File configFile = new File(dumpPath, "dummyConfig.xml");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(configFile, false));
            xstream.toXML(wc, writer);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    public static void dumpConfig(@Nonnull XStream xstream, @Nonnull Object config) throws IOException {
        File configFile = new File(dumpPath, "dump.xml");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(configFile, false));
            xstream.toXML(config, writer);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}
