package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Entity.Anuncio;
import Entity.DenunciaAnuncio;
import Entity.Destaque;
import Entity.Evento;
import Entity.Favorito;
import Entity.Foto;
import Entity.Marca;
import Entity.Modelo;
import Entity.Usuario;

public class DAO extends SQLiteOpenHelper {

    private Context context;

    public DAO (Context context){
        super(context, "old02", null, 23);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tb_usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "cpf TEXT," +
                "telefone TEXT," +
                "email TEXT," +
                "senha TEXT," +
                "ativo INTEGER," +
                "foto TEXT," +
                "adm INTEGER)";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_marca (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT)";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_foto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "foto TEXT)";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_fotoAnuncio ("+
                "id_foto INTEGER,"+
                "id_anuncio INTEGER,"+
                "PRIMARY KEY (id_foto, id_anuncio),"+
                "FOREIGN KEY (id_foto) REFERENCES tb_foto(id),"+
                "FOREIGN KEY (id_anuncio) REFERENCES tb_anuncio(id));";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_denunciaAnuncio ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_anuncio INTEGER,"+
                "id_usuario INTEGER,"+
                "ativo INTEGER,"+
                "descricao TEXT,"+
                "FOREIGN KEY (id_anuncio) REFERENCES tb_anuncio(id),"+
                "FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id));";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_favorito ("+
                "id_anuncio INTEGER,"+
                "id_usuario INTEGER,"+
                "PRIMARY KEY (id_anuncio, id_usuario),"+
                "FOREIGN KEY (id_anuncio) REFERENCES tb_anuncio(id),"+
                "FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id));";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_modelo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "marca INTEGER," +
                "FOREIGN KEY (marca) REFERENCES tb_marca(id));";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_anuncio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "anoFabricacao INTEGER," +
                "anoModelo INTEGER," +
                "cor TEXT," +
                "km TEXT," +
                "titulo TEXT," +
                "combustivel TEXT," +
                "cambio TEXT," +
                "troca TEXT," +
                "ativo INTEGER," +
                "descricao TEXT," +
                "produto INTEGER," +
                "data TEXT," +
                "usuario INTEGER," +
                "modelo INTEGER," +
                "foto TEXT," +
                "preco TEXT," +
                "FOREIGN KEY (usuario) REFERENCES tb_usuario(id)," +
                "FOREIGN KEY (modelo) REFERENCES tb_modelo(id));";
        db.execSQL(sql);


        sql =   "CREATE TABLE tb_evento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT," +
                "data TEXT," +
                "local TEXT," +
                "descricao TEXT," +
                "foto TEXT," +
                "usuario INTEGER," +
                "ativo INTEGER," +
                "FOREIGN KEY (usuario) REFERENCES tb_usuario(id));";
        db.execSQL(sql);

        sql =   "CREATE TABLE tb_destaque ("+
                "id INTEGER PRIMARY KEY NOT NULL,"+
                "id_evento INTEGER,"+
                "titulo TEXT,"+
                "posicao TEXT,"+
                "premio TEXT,"+
                "foto TEXT," +
                "descricao TEXT,"+
                "FOREIGN KEY (id_evento) REFERENCES tb_evento(id));";
        db.execSQL(sql);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_usuario;");
        db.execSQL("DROP TABLE IF EXISTS tb_marca;");
        db.execSQL("DROP TABLE IF EXISTS tb_modelo;");
        db.execSQL("DROP TABLE IF EXISTS tb_anuncio;");
        db.execSQL("DROP TABLE IF EXISTS tb_destaque;");
        db.execSQL("DROP TABLE IF EXISTS tb_foto;");
        db.execSQL("DROP TABLE IF EXISTS tb_fotoAnuncio;");
        db.execSQL("DROP TABLE IF EXISTS tb_evento;");
        db.execSQL("DROP TABLE IF EXISTS tb_favorito;");
        db.execSQL("DROP TABLE IF EXISTS tb_denunciaAnuncio;");


        onCreate(db);
    }


    //      USUARIO      ---------------------------------
    public void inserirUsuario(Usuario usuario){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", usuario.getNome());
        dados.put("cpf", usuario.getCpf());
        dados.put("telefone", usuario.getTelefone());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("adm", usuario.getAdm());
        dados.put("foto", usuario.getFoto());
        dados.put("ativo", usuario.getAtivo());

        db.insert("tb_usuario",null,dados);
    }
    public void atualizarPessoa(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        // Adicione os novos valores
        dados.put("nome", usuario.getNome());
        dados.put("cpf", usuario.getCpf());
        dados.put("telefone", usuario.getTelefone());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("adm", usuario.getAdm());
        dados.put("foto", usuario.getFoto());
        dados.put("ativo", usuario.getAtivo());
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(usuario.getId()) };

        db.update("tb_usuario", dados, whereClause, whereArgs);
    }
    @SuppressLint("Range")
    public List<Usuario> usuarios(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_usuario;";

        Cursor c = db.rawQuery(sql,null);
        List<Usuario> usuarios = new ArrayList<Usuario>();
        while ((c.moveToNext())){
            Usuario usuario = new Usuario();
            usuario.setId(c.getInt(c.getColumnIndex("id")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            usuario.setCpf(c.getString(c.getColumnIndex("cpf")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));
            usuario.setAdm(c.getInt(c.getColumnIndex("adm")));
            usuario.setFoto(c.getString(c.getColumnIndex("foto")));
            usuario.setAtivo(c.getInt(c.getColumnIndex("ativo")));
            usuarios.add(usuario);
        }
        return usuarios;
    }


    @SuppressLint("Range")
    public List<Usuario> adms(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_usuario WHERE adm = 1;";

        Cursor c = db.rawQuery(sql,null);
        List<Usuario> usuarios = new ArrayList<Usuario>();
        while ((c.moveToNext())){
            Usuario usuario = new Usuario();
            usuario.setId(c.getInt(c.getColumnIndex("id")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            usuario.setCpf(c.getString(c.getColumnIndex("cpf")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));
            usuario.setAdm(c.getInt(c.getColumnIndex("adm")));
            usuario.setFoto(c.getString(c.getColumnIndex("foto")));
            usuario.setAtivo(c.getInt(c.getColumnIndex("ativo")));
            usuarios.add(usuario);
        }
        return usuarios;
    }

    @SuppressLint("Range")
    public Usuario usuarioEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_usuario WHERE email = ?;";

        Cursor c = db.rawQuery(sql, new String[]{email});
        Usuario usuario = null;
        if (c.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(c.getInt(c.getColumnIndex("id")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            usuario.setCpf(c.getString(c.getColumnIndex("cpf")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));
            usuario.setAdm(c.getInt(c.getColumnIndex("adm")));
            usuario.setFoto(c.getString(c.getColumnIndex("foto")));
            usuario.setAtivo(c.getInt(c.getColumnIndex("ativo")));
        }
        c.close();
        return usuario;
    }

    @SuppressLint("Range")
    public Usuario usuarioId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        // Altere a consulta para incluir a cláusula WHERE
        String sql = "SELECT * FROM tb_usuario WHERE id = ?;";

        // Crie um Cursor para executar a consulta
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});

        // Inicialize o objeto Usuario
        Usuario usuario = null;

        // Verifique se o cursor contém dados
        if (c.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(c.getInt(c.getColumnIndex("id")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setTelefone(c.getString(c.getColumnIndex("telefone")));
            usuario.setCpf(c.getString(c.getColumnIndex("cpf")));
            usuario.setEmail(c.getString(c.getColumnIndex("email")));
            usuario.setSenha(c.getString(c.getColumnIndex("senha")));
            usuario.setAdm(c.getInt(c.getColumnIndex("adm")));
            usuario.setFoto(c.getString(c.getColumnIndex("foto")));
            usuario.setAtivo(c.getInt(c.getColumnIndex("ativo")));
        }

        // Feche o cursor
        c.close();

        return usuario; // Retorna o usuário encontrado ou null se não existir
    }






    //      MARCA      ---------------------------------
    public void inserirMarca(Marca marca){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", marca.getNome());

        db.insert("tb_marca",null,dados);
    }
    @SuppressLint("Range")
    public List<Marca> marcas(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_marca order by nome;";

        Cursor c = db.rawQuery(sql,null);
        List<Marca> marcas = new ArrayList<Marca>();
        while ((c.moveToNext())){
            Marca marca = new Marca();
            marca.setId(c.getInt(c.getColumnIndex("id")));
            marca.setNome(c.getString(c.getColumnIndex("nome")));
            marcas.add(marca);
        }
        return marcas;
    }
    @SuppressLint("Range")
    public Marca marcaId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_marca WHERE id = ?;";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});

        Marca marca = null;
        if (c.moveToFirst()) {
            marca = new Marca();
            marca.setId(c.getInt(c.getColumnIndex("id")));
            marca.setNome(c.getString(c.getColumnIndex("nome")));
        }
        c.close(); // Don't forget to close the cursor
        return marca;
    }




    //      MODELO      ---------------------------------
    public void inserirModelo(Modelo modelo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("nome", modelo.getNome());
        dados.put("marca", modelo.getMarca().getId());

        db.insert("tb_modelo",null,dados);
    }

    public void excluirModelosComMarca8() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tb_modelo", "marca = ?", new String[]{"8"});
    }


    @SuppressLint("Range")
    public List<Modelo> Modelos(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_modelo;";

        Cursor c = db.rawQuery(sql,null);
        List<Modelo> modelos = new ArrayList<Modelo>();
        while ((c.moveToNext())){
            Modelo modelo = new Modelo();
            modelo.setId(c.getInt(c.getColumnIndex("id")));
            modelo.setNome(c.getString(c.getColumnIndex("nome")));
            modelo.setMarca(marcaId(c.getInt(c.getColumnIndex("marca"))));
            modelos.add(modelo);
        }
        return modelos;
    }

    @SuppressLint("Range")
    public List<Modelo> modelosMarca(int marcaId) {
        SQLiteDatabase db = getReadableDatabase();
        // Altere a consulta SQL para incluir WHERE e ORDER BY
        String sql = "SELECT * FROM tb_modelo WHERE marca = ? ORDER BY nome ASC";

        // Use argumentos para evitar SQL injection
        String[] selectionArgs = new String[]{String.valueOf(marcaId)};

        Cursor c = db.rawQuery(sql, selectionArgs);
        List<Modelo> modelos = new ArrayList<>();
        while (c.moveToNext()) {
            Modelo modelo = new Modelo();
            modelo.setId(c.getInt(c.getColumnIndex("id")));
            modelo.setNome(c.getString(c.getColumnIndex("nome")));
            modelo.setMarca(marcaId(c.getInt(c.getColumnIndex("marca")))); // Certifique-se de que marcaId está retornando um valor válido
            modelos.add(modelo);
        }
        c.close(); // Sempre feche o Cursor após o uso
        return modelos;
    }

    @SuppressLint("Range")
    public Modelo modeloID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        // Altere a consulta SQL para incluir WHERE e ORDER BY
        String sql = "SELECT * FROM tb_modelo WHERE id = ? ";

        // Use argumentos para evitar SQL injection
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor c = db.rawQuery(sql, selectionArgs);
        Modelo modelo = new Modelo();
        while (c.moveToNext()) {
            modelo.setId(c.getInt(c.getColumnIndex("id")));
            modelo.setNome(c.getString(c.getColumnIndex("nome")));
            modelo.setMarca(marcaId(c.getInt(c.getColumnIndex("marca")))); // Certifique-se de que marcaId está retornando um valor válido

        }
        c.close(); // Sempre feche o Cursor após o uso
        return modelo;
    }




    //      ANUNCIO      ---------------------------------
    public long inserirAnuncio(Anuncio anuncio) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("anoFabricacao", anuncio.getAnoFabricacao());
        dados.put("anoModelo", anuncio.getAnoModelo());
        dados.put("cor", anuncio.getCor());
        dados.put("km", anuncio.getKm());
        dados.put("titulo", anuncio.getTitulo());
        dados.put("combustivel", anuncio.getCombustivel());
        dados.put("cambio", anuncio.getCambio());
        dados.put("troca", anuncio.getTroca());
        dados.put("ativo", anuncio.getAtivo());
        dados.put("descricao", anuncio.getDescricao());
        dados.put("produto", anuncio.getProduto());
        dados.put("data", anuncio.getData());
        dados.put("usuario", anuncio.getUsuario().getId());
        dados.put("modelo", anuncio.getModelo().getId());
        dados.put("foto", anuncio.getFoto());
        dados.put("preco", anuncio.getPreco());

        long id = db.insert("tb_anuncio", null, dados);
        db.close();
        return id;
    }

    public void atualizarAnucio(Anuncio anuncio) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("anoFabricacao", anuncio.getAnoFabricacao());
        dados.put("anoModelo", anuncio.getAnoModelo());
        dados.put("cor", anuncio.getCor());
        dados.put("km", anuncio.getKm());
        dados.put("titulo", anuncio.getTitulo());
        dados.put("combustivel", anuncio.getCombustivel());
        dados.put("cambio", anuncio.getCambio());
        dados.put("troca", anuncio.getTroca());
        dados.put("ativo", anuncio.getAtivo());
        dados.put("descricao", anuncio.getDescricao());
        dados.put("produto", anuncio.getProduto());
        dados.put("data", anuncio.getData());
        dados.put("usuario", anuncio.getUsuario().getId());
        dados.put("modelo", anuncio.getModelo().getId());
        dados.put("foto", anuncio.getFoto());
        dados.put("preco", anuncio.getPreco());
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(anuncio.getId()) };

        db.update("tb_anuncio", dados, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public List<Anuncio> anuncios(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_anuncio WHERE ativo = 1 ORDER BY id DESC;";

        
        Cursor c = db.rawQuery(sql,null);
        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        while ((c.moveToNext())){
            Anuncio anuncio = new Anuncio();
            anuncio.setId(c.getInt(c.getColumnIndex("id")));
            anuncio.setAnoFabricacao(c.getInt(c.getColumnIndex("anoFabricacao")));
            anuncio.setAnoModelo(c.getInt(c.getColumnIndex("anoModelo")));
            anuncio.setCor(c.getString(c.getColumnIndex("cor")));
            anuncio.setKm(c.getString(c.getColumnIndex("km")));
            anuncio.setTitulo(c.getString(c.getColumnIndex("titulo")));
            anuncio.setCombustivel(c.getString(c.getColumnIndex("combustivel")));
            anuncio.setCambio(c.getString(c.getColumnIndex("cambio")));
            anuncio.setTroca(c.getString(c.getColumnIndex("troca")));
            anuncio.setAtivo(c.getInt(c.getColumnIndex("ativo")));
            anuncio.setDescricao(c.getString(c.getColumnIndex("descricao")));
            anuncio.setProduto(c.getInt(c.getColumnIndex("produto")));
            anuncio.setData(c.getString(c.getColumnIndex("data")));
            anuncio.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            anuncio.setModelo(modeloID(c.getInt(c.getColumnIndex("modelo"))));
            anuncio.setFoto((c.getString(c.getColumnIndex("foto"))));
            anuncio.setPreco((c.getString(c.getColumnIndex("preco"))));


            anuncios.add(anuncio);
        }
        return anuncios;
    }

    @SuppressLint("Range")
    public Anuncio anuncioId(int id ){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_anuncio WHERE id = ?;";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});

        if (c != null) {
            c.moveToFirst();
            Anuncio anuncio = new Anuncio();
            anuncio.setId(c.getInt(c.getColumnIndex("id")));
            anuncio.setAnoFabricacao(c.getInt(c.getColumnIndex("anoFabricacao")));
            anuncio.setAnoModelo(c.getInt(c.getColumnIndex("anoModelo")));
            anuncio.setCor(c.getString(c.getColumnIndex("cor")));
            anuncio.setKm(c.getString(c.getColumnIndex("km")));
            anuncio.setTitulo(c.getString(c.getColumnIndex("titulo")));
            anuncio.setCombustivel(c.getString(c.getColumnIndex("combustivel")));
            anuncio.setCambio(c.getString(c.getColumnIndex("cambio")));
            anuncio.setTroca(c.getString(c.getColumnIndex("troca")));
            anuncio.setAtivo(c.getInt(c.getColumnIndex("ativo")));
            anuncio.setDescricao(c.getString(c.getColumnIndex("descricao")));
            anuncio.setProduto(c.getInt(c.getColumnIndex("produto")));
            anuncio.setData(c.getString(c.getColumnIndex("data")));
            anuncio.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            anuncio.setModelo(modeloID(c.getInt(c.getColumnIndex("modelo"))));
            anuncio.setFoto((c.getString(c.getColumnIndex("foto"))));
            anuncio.setPreco((c.getString(c.getColumnIndex("preco"))));

            c.close();
            return anuncio;
        }
        return null;
    }


    @SuppressLint("Range")
    public List<Anuncio> anunciosUsuario(int id ){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_anuncio WHERE usuario = ?;";
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});

        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        while ((c.moveToNext())){
            Anuncio anuncio = new Anuncio();
            anuncio.setId(c.getInt(c.getColumnIndex("id")));
            anuncio.setAnoFabricacao(c.getInt(c.getColumnIndex("anoFabricacao")));
            anuncio.setAnoModelo(c.getInt(c.getColumnIndex("anoModelo")));
            anuncio.setCor(c.getString(c.getColumnIndex("cor")));
            anuncio.setKm(c.getString(c.getColumnIndex("km")));
            anuncio.setTitulo(c.getString(c.getColumnIndex("titulo")));
            anuncio.setCombustivel(c.getString(c.getColumnIndex("combustivel")));
            anuncio.setCambio(c.getString(c.getColumnIndex("cambio")));
            anuncio.setTroca(c.getString(c.getColumnIndex("troca")));
            anuncio.setAtivo(c.getInt(c.getColumnIndex("ativo")));
            anuncio.setDescricao(c.getString(c.getColumnIndex("descricao")));
            anuncio.setProduto(c.getInt(c.getColumnIndex("produto")));
            anuncio.setData(c.getString(c.getColumnIndex("data")));
            anuncio.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            anuncio.setModelo(modeloID(c.getInt(c.getColumnIndex("modelo"))));
            anuncio.setFoto((c.getString(c.getColumnIndex("foto"))));
            anuncio.setPreco((c.getString(c.getColumnIndex("preco"))));


            anuncios.add(anuncio);
        }
        return anuncios;
    }







    //FOTO
    public long inserirFoto(Foto foto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("foto", foto.getFoto());

        long id = db.insert("tb_foto", null, dados);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public Foto fotoID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        // Altere a consulta SQL para incluir WHERE e ORDER BY
        String sql = "SELECT * FROM tb_foto WHERE id = ? ";

        // Use argumentos para evitar SQL injection
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor c = db.rawQuery(sql, selectionArgs);
        Foto foto = new Foto();
        while (c.moveToNext()) {
            foto.setId(c.getInt(c.getColumnIndex("id")));
            foto.setFoto(c.getString(c.getColumnIndex("foto")));

        }
        c.close(); // Sempre feche o Cursor após o uso
        return foto;
    }



    //FOTO ANUNCIO

    public long inserirFotoAnuncio(long foto, long anuncio){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("id_foto", foto);
        dados.put("id_anuncio", anuncio);

        long id = db.insert("tb_fotoAnuncio", null, dados);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Foto> fotosAnuncios(int id) {
        SQLiteDatabase db = getReadableDatabase();
        // Altere a consulta SQL para incluir WHERE e ORDER BY
        String sql = "SELECT * FROM tb_fotoAnuncio WHERE id_anuncio = ? ";

        // Use argumentos para evitar SQL injection
        String[] selectionArgs = new String[]{String.valueOf(id)};
        List<Foto> fotos = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            Foto foto = new Foto();
            int ID_FOTO = c.getInt(c.getColumnIndex("id_foto"));
            foto = fotoID(ID_FOTO);
            fotos.add(foto);


        }
        c.close(); // Sempre feche o Cursor após o uso
        return fotos;
    }













    //EVENTO

    public long inserirEvento(Evento evento) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("titulo", evento.getTitulo());
        dados.put("data", evento.getData());
        dados.put("descricao", evento.getDescricao());
        dados.put("local", evento.getLocal());
        dados.put("ativo", evento.getAtivo());
        dados.put("foto", evento.getFoto());
        dados.put("usuario", evento.getUsuario().getId());



        long id = db.insert("tb_evento", null, dados);
        db.close();
        return id;
    }

    public void atualizarEvento(Evento evento) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("titulo", evento.getTitulo());
        dados.put("data", evento.getData());
        dados.put("descricao", evento.getDescricao());
        dados.put("local", evento.getLocal());
        dados.put("ativo", evento.getAtivo());
        dados.put("foto", evento.getFoto());
        dados.put("usuario", evento.getUsuario().getId());
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(evento.getId()) };

        db.update("tb_evento", dados, whereClause, whereArgs);
    }


    @SuppressLint("Range")
    public List<Evento> eventosAtivos(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE ativo = 1;";

        Cursor c = db.rawQuery(sql,null);
        List<Evento> eventos = new ArrayList<Evento>();
        while ((c.moveToNext())){
            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            eventos.add(evento);
        }
        c.close();
        return eventos;
    }

    @SuppressLint("Range")
    public List<Evento> eventosEmAnalise(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE ativo = 2;";

        Cursor c = db.rawQuery(sql,null);
        List<Evento> eventos = new ArrayList<Evento>();
        while ((c.moveToNext())){
            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            eventos.add(evento);
        }
        c.close();
        return eventos;
    }

    @SuppressLint("Range")
    public List<Evento> eventosDesativados(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE ativo = 0;";

        Cursor c = db.rawQuery(sql,null);
        List<Evento> eventos = new ArrayList<Evento>();
        while ((c.moveToNext())){
            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            eventos.add(evento);
        }
        c.close();
        return eventos;
    }

    @SuppressLint("Range")
    public List<Evento> eventosAnalise(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE ativo = 2;";

        Cursor c = db.rawQuery(sql,null);
        List<Evento> eventos = new ArrayList<Evento>();
        while ((c.moveToNext())){
            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            eventos.add(evento);
        }
        c.close();
        return eventos;
    }

    @SuppressLint("Range")
    public List<Evento> eventosUsuario(int usuarioId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE  usuario = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(usuarioId)});
        List<Evento> eventos = new ArrayList<Evento>();
        while (c.moveToNext()) {
            Evento evento = new Evento();
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

            eventos.add(evento);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return eventos;
    }

    @SuppressLint("Range")
    public Evento eventoId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_evento WHERE  id = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        Evento evento = new Evento();
        while (c.moveToNext()) {
            evento.setId(c.getInt(c.getColumnIndex("id")));
            evento.setTitulo(c.getString(c.getColumnIndex("titulo")));
            evento.setLocal(c.getString(c.getColumnIndex("local")));
            evento.setDescricao(c.getString(c.getColumnIndex("descricao")));
            evento.setData(c.getString(c.getColumnIndex("data")));
            evento.setFoto(c.getString(c.getColumnIndex("foto")));
            evento.setUsuario(usuarioId(c.getInt(c.getColumnIndex("usuario"))));
            evento.setAtivo(c.getInt(c.getColumnIndex("ativo")));

        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return evento;
    }










    //          FAVORITOS

    public long inserirfavorito(long usuario, long  anuncio){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("id_usuario", anuncio);
        dados.put("id_anuncio", usuario);

        long id = db.insert("tb_favorito", null, dados);
        db.close();
        return id;
    }

    public int deletarFavorito(long usuarioId, long anuncioId) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete("tb_favorito", "id_usuario = ? AND id_anuncio = ?", new String[]{String.valueOf(usuarioId), String.valueOf(anuncioId)});
        db.close();
        return rowsDeleted;
    }

    @SuppressLint("Range")
    public List<Favorito> favoritos(int usuarioId, int anuncioId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_favorito WHERE id_usuario = ? AND id_anuncio = ?";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(usuarioId), String.valueOf(anuncioId)});
        List<Favorito> favoritos = new ArrayList<>();
        while (c.moveToNext()) {
            Favorito favorito = new Favorito();
            favorito.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            favorito.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));

            favoritos.add(favorito);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return favoritos;
    }

    @SuppressLint("Range")
    public List<Favorito> todosFavoritos() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_favorito;";

        Cursor c = db.rawQuery(sql, new String[]{});
        List<Favorito> favoritos = new ArrayList<>();
        while (c.moveToNext()) {
            Favorito favorito = new Favorito();
            favorito.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            favorito.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));

            favoritos.add(favorito);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return favoritos;
    }

    @SuppressLint("Range")
    public List<Favorito> todosFavoritos2() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_favorito;";

        Cursor c = db.rawQuery(sql, new String[]{});
        List<Favorito> favoritos = new ArrayList<>();
        while (c.moveToNext()) {
            Favorito favorito = new Favorito();
            favorito.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            favorito.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));

            favoritos.add(favorito);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return favoritos;
    }


    @SuppressLint("Range")
    public List<Favorito> favoritosDoUsuario(int usuarioId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_favorito WHERE  id_usuario = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(usuarioId)});
        List<Favorito> favoritos = new ArrayList<>();
        while (c.moveToNext()) {
            Favorito favorito = new Favorito();
            favorito.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            favorito.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));

            favoritos.add(favorito);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return favoritos;
    }

    public Boolean itemSalvo(int usuarioId, int anuncioId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_favorito WHERE id_usuario = ? AND id_anuncio = ?;";

        // Corrigir a criação do cursor
        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(usuarioId), String.valueOf(anuncioId)});

        boolean encontrado = false;
        if (c.moveToFirst()) {
            encontrado = true;
        }

        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return encontrado;
    }






    // DENUNCIA ANUNCIO

    public long inserirDenunciaAnuncio (long usuario, long  anuncio, String descricao){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("id_usuario",  usuario);
        dados.put("id_anuncio", anuncio);
        dados.put("descricao", descricao);
        dados.put("ativo", 1);

        long id = db.insert("tb_denunciaAnuncio", null, dados);
        db.close();
        return id;
    }

    public void atualizarDenuncia(DenunciaAnuncio denunciaAnuncio) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("id_usuario", denunciaAnuncio.getUsuario().getId());
        dados.put("id_anuncio", denunciaAnuncio.getAnuncio().getId());
        dados.put("descricao", denunciaAnuncio.getDescricao());
        dados.put("ativo", denunciaAnuncio.getAtivo());
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(denunciaAnuncio.getId()) };

        db.update("tb_denunciaAnuncio", dados, whereClause, whereArgs);
    }

    @SuppressLint("Range")
    public List<DenunciaAnuncio> denuncias() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_denunciaAnuncio WHERE  ativo = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(1)});
        List<DenunciaAnuncio> denuncias = new ArrayList<DenunciaAnuncio>();
        while (c.moveToNext()) {
            DenunciaAnuncio denuncia = new DenunciaAnuncio();
            denuncia.setId(c.getInt(c.getColumnIndex("id")));
            denuncia.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            denuncia.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));
            denuncia.setDescricao(c.getString(c.getColumnIndex("descricao")));

            denuncias.add(denuncia);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return denuncias;
    }

    @SuppressLint("Range")
    public DenunciaAnuncio denunciasId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_denunciaAnuncio WHERE  id = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        DenunciaAnuncio denuncia = new DenunciaAnuncio();
        while (c.moveToNext()) {

            denuncia.setId(c.getInt(c.getColumnIndex("id")));
            denuncia.setAnuncio(anuncioId(c.getInt(c.getColumnIndex("id_anuncio"))));
            denuncia.setUsuario(usuarioId(c.getInt(c.getColumnIndex("id_usuario"))));
            denuncia.setDescricao(c.getString(c.getColumnIndex("descricao")));
        }

        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return denuncia;
    }




    //////          ------- DESTAQUE


    public long inserirDestaque(Destaque destaque) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("titulo", destaque.getTitulo());
        dados.put("posicao", destaque.getPosicao());
        dados.put("premio", destaque.getPremio());
        dados.put("descricao", destaque.getDescricao());
        dados.put("foto", destaque.getFoto());
        dados.put("id_evento", destaque.getEvento().getId());



        long id = db.insert("tb_destaque", null, dados);
        db.close();
        return id;
    }



    @SuppressLint("Range")
    public List<Destaque> destaqueEventoId(int eventoId) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_destaque WHERE  id_evento = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(eventoId)});
        List<Destaque> destaques = new ArrayList<>();
        while (c.moveToNext()) {
            Destaque destaque = new Destaque();
            destaque.setId(c.getInt(c.getColumnIndex("id")));
            destaque.setEvento(eventoId(c.getInt(c.getColumnIndex("id_evento"))));
            destaque.setTitulo(c.getString(c.getColumnIndex("titulo")));
            destaque.setPosicao(c.getString(c.getColumnIndex("posicao")));
            destaque.setDescricao(c.getString(c.getColumnIndex("descricao")));
            destaque.setFoto(c.getString(c.getColumnIndex("foto")));
            destaque.setPremio(c.getString(c.getColumnIndex("premio")));


            destaques.add(destaque);
        }
        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return destaques;
    }

    @SuppressLint("Range")
    public Destaque destaqueId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tb_destaque WHERE  id = ?;";

        Cursor c = db.rawQuery(sql, new String[]{String.valueOf(id)});
        Destaque destaque = new Destaque();
        while (c.moveToNext()) {
            destaque.setId(c.getInt(c.getColumnIndex("id")));
            destaque.setEvento(eventoId(c.getInt(c.getColumnIndex("id_evento"))));
            destaque.setTitulo(c.getString(c.getColumnIndex("titulo")));
            destaque.setPosicao(c.getString(c.getColumnIndex("posicao")));
            destaque.setDescricao(c.getString(c.getColumnIndex("descricao")));
            destaque.setFoto(c.getString(c.getColumnIndex("foto")));
            destaque.setPremio(c.getString(c.getColumnIndex("premio")));
        }

        c.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
        return destaque;
    }


    public void removerDestaque(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tb_destaque", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }












}
