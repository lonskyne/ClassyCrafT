package raf.dsw.classycraft.app.serializer.CustomSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import raf.dsw.classycraft.app.core.ProjectTreeAbstraction.ClassyNode;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.Package;
import raf.dsw.classycraft.app.core.ProjectTreeImplementation.Project;

import java.io.IOException;

public class ProjectSerializer extends StdSerializer<Project> {
    public ProjectSerializer() {
        this(null);
    }

    protected ProjectSerializer(Class<Project> t) {
        super(t);
    }

    @Override
    public void serialize(Project project, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("project name", project.getName());
        jsonGenerator.writeStringField("author", project.getAuthor());
        jsonGenerator.writeStringField("path", project.getLocalPath());
        // jsonGenerator.writeArrayFieldStart("packages");
        // jsonGenerator.writeStartArray(project.getChildren());
        //jsonGenerator.writeArrayFieldStart("packages");
        jsonGenerator.writeArrayFieldStart("packages");

        for (ClassyNode cn : project.getChildren())
            jsonGenerator.writeObject((Package) cn);

        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
