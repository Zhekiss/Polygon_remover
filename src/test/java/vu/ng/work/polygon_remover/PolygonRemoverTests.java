package vu.ng.work.polygon_remover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vu.ng.work.polygon_remover.math.*;
import vu.ng.work.polygon_remover.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PolygonRemoverTests {
    private Model model;

    @BeforeEach
    public void init() {
        model = new Model();
        // Khởi tạo vertices
        model.vertices.add(new Vector3f(0, 0, 0));   // 0
        model.vertices.add(new Vector3f(1, 0, 0));   // 1
        model.vertices.add(new Vector3f(0, 1, 0));   // 2
        model.vertices.add(new Vector3f(1, 1, 0));   // 3
        model.vertices.add(new Vector3f(0, 0, 1));   // 4
        model.vertices.add(new Vector3f(1, 0, 1));   // 5
        model.vertices.add(new Vector3f(0, 1, 1));   // 6
        model.vertices.add(new Vector3f(1, 1, 1));   // 7

        // Khởi tạo polygons
        Polygon polygon1 = createPolygon(0, 1, 3, 2);
        Polygon polygon2 = createPolygon(4, 5, 7, 6);
        Polygon polygon3 = createPolygon(0, 1, 5, 4);
        Polygon polygon4 = createPolygon(2, 3, 7, 6);
        Polygon polygon5 = createPolygon(0, 2, 6, 4);
        Polygon polygon6 = createPolygon(1, 3, 7, 5);

        model.polygons.addAll(List.of(polygon1, polygon2, polygon3, polygon4, polygon5, polygon6));
    }

    // Helper method tạo polygon
    private Polygon createPolygon(Integer... vertexIndices) {
        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(List.of(vertexIndices)));
        return polygon;
    }

    @Test
    @DisplayName("Remove Single Polygon")
    public void testRemoveSinglePolygon() {
        // Số lượng polygon ban đầu
        int initialPolygonCount = model.polygons.size();

        // Xóa polygon đầu tiên
        PolygonRemover.removePolygons(model, List.of(0));

        // Kiểm tra số lượng polygon giảm
        assertEquals(initialPolygonCount - 1, model.polygons.size(),
                "Số lượng polygon phải giảm 1");
    }

    @Test
    @DisplayName("Remove Multiple Polygons")
    public void testRemoveMultiplePolygons() {
        // Số lượng polygon ban đầu
        int initialPolygonCount = model.polygons.size();

        // Xóa 2 polygon đầu tiên
        PolygonRemover.removePolygons(model, List.of(0, 1));

        // Kiểm tra số lượng polygon giảm
        assertEquals(initialPolygonCount - 2, model.polygons.size(),
                "Số lượng polygon phải giảm 2");
    }

    @Test
    @DisplayName("Remove Polygons Affecting Vertices")
    public void testRemovePolygonsAffectingVertices() {
        // Số lượng vertex ban đầu
        int initialVertexCount = model.vertices.size();

        // Xóa các polygon liên quan đến một số vertex
        PolygonRemover.removePolygons(model, List.of(0, 2, 4));

        // Kiểm tra số lượng vertex và polygon
        assertTrue(model.vertices.size() < initialVertexCount,
                "Số lượng vertex phải giảm");
        assertEquals(0, model.polygons.size(),
                "Tất cả polygon phải bị xóa");
    }

    @Test
    @DisplayName("Remove Non-Existent Polygons")
    public void testRemoveNonExistentPolygons() {
        // Số lượng polygon ban đầu
        int initialPolygonCount = model.polygons.size();

        // Thử xóa polygon không tồn tại
        PolygonRemover.removePolygons(model, List.of(10, 20));

        // Kiểm tra không có thay đổi
        assertEquals(initialPolygonCount, model.polygons.size(),
                "Số lượng polygon không được thay đổi");
    }

    @Test
    @DisplayName("Remove All Polygons")
    public void testRemoveAllPolygons() {
        // Xóa tất cả polygon
        List<Integer> allPolygonIndices = new ArrayList<>();
        for (int i = 0; i < model.polygons.size(); i++) {
            allPolygonIndices.add(i);
        }

        PolygonRemover.removePolygons(model, allPolygonIndices);

        // Kiểm tra không còn polygon
        assertEquals(0, model.polygons.size(),
                "Tất cả polygon phải bị xóa");

        // Kiểm tra không còn vertex
        assertEquals(0, model.vertices.size(),
                "Tất cả vertex phải bị xóa");
    }

    @Test
    @DisplayName("Remove Polygons with Empty List")
    public void testRemoveEmptyPolygonList() {
        // Số lượng polygon ban đầu
        int initialPolygonCount = model.polygons.size();
        int initialVertexCount = model.vertices.size();

        // Thử xóa với danh sách rỗng
        PolygonRemover.removePolygons(model, new ArrayList<>());

        // Kiểm tra không có thay đổi
        assertEquals(initialPolygonCount, model.polygons.size(),
                "Số lượng polygon không được thay đổi");
        assertEquals(initialVertexCount, model.vertices.size(),
                "Số lượng vertex không được thay đổi");
    }

    @Test
    @DisplayName("Remove Null Polygons List")
    public void testRemoveNullPolygonList() {
        // Số lượng polygon ban đầu
        int initialPolygonCount = model.polygons.size();
        int initialVertexCount = model.vertices.size();

        // Thử xóa với danh sách null
        PolygonRemover.removePolygons(model, null);

        // Kiểm tra không có thay đổi
        assertEquals(initialPolygonCount, model.polygons.size(),
                "Số lượng polygon không được thay đổi");
        assertEquals(initialVertexCount, model.vertices.size(),
                "Số lượng vertex không được thay đổi");
    }
}
