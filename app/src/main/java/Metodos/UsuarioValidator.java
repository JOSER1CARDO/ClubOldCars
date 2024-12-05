package Metodos;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

import DAO.DAO;
import Entity.Usuario;

public class UsuarioValidator {


    public static String validaDados(Context context,EditText txtNome, EditText txtCpf, EditText txtTelefone, EditText txtEmail, EditText txtSenha, EditText txtSenhaConfirma, Boolean check){
        Usuario pessoa=  new Usuario();

        DAO dao = new DAO(context.getApplicationContext());
        String nome = txtNome.getText().toString().trim();
        String cpf = txtCpf.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String telefone = txtTelefone.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();
        String senhaConfirma = txtSenhaConfirma.getText().toString().trim();

        if(nome.equals("")){
            return "INSIRA UM NOME PARA CONTINUAR";
        }else if (nome.length() < 10){
            return "O NOME DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (nome.length() > 50){
            return "O NOME DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(CPFValidator.isValidCPF(cpf))) {
            return "CPF INVALIDO";
        }else if((telefone.equals(""))){
            return "TELEFONE INVALIDO";
        }else if (email.equals("")){
            return "INSIRA UM EMAIL PARA CONTINUAR";
        }else if (email.length() < 10){
            return "O EMAIL DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (email.length() > 50){
            return "O EMAIL DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(EmailValidator.isValidEmail(email))) {
            return "EMAIL INVALIDO";
        }else if (senha.equals("")){
            return "INSIRA UMA SENHA PARA CONTINUAR";
        }else if (senha.length() < 10){
            return "A SENHA DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (senha.length() > 50){
            return "A SENHA DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(senhaConfirma.equals(senha))){
            return "AS SENHAS NAO SAO IGUAIS";
        }else if(check.equals(false)){
            return "ACEITE OS TERMOS DE USO";
        }else{
            List<Usuario> usuarios = dao.usuarios();
            for(Usuario p:usuarios){
                if(p.getEmail().trim().equals(email)){
                    return "ESTE EMAIL JA ESTA SENDO USADO";
                }
                if(p.getCpf().trim().equals(cpf)){
                    return "ESTE CPF JA ESTA SENDO USADO";
                }
            }
            return"ok";
        }
    }

    public static String alteraDados(Usuario usuario, Context context,EditText txtNome, EditText txtCpf, EditText txtTelefone, EditText txtEmail, EditText txtSenha, EditText txtSenhaConfirma, Boolean check){
        Usuario pessoa=  new Usuario();

        DAO dao = new DAO(context.getApplicationContext());
        String nome = txtNome.getText().toString().trim();
        String cpf = txtCpf.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String telefone = txtTelefone.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();
        String senhaConfirma = txtSenhaConfirma.getText().toString().trim();

        if(nome.equals("")){
            return "INSIRA UM NOME PARA CONTINUAR";
        }else if (nome.length() < 10){
            return "O NOME DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (nome.length() > 50){
            return "O NOME DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(CPFValidator.isValidCPF(cpf))) {
            return "CPF INVALIDO";
        }else if((telefone.equals(""))){
            return "TELEFONE INVALIDO";
        }else if (email.equals("")){
            return "INSIRA UM EMAIL PARA CONTINUAR";
        }else if (email.length() < 10){
            return "O EMAIL DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (email.length() > 50){
            return "O EMAIL DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(EmailValidator.isValidEmail(email))) {
            return "EMAIL INVALIDO";
        }else if (senha.equals("")){
            return "INSIRA UMA SENHA PARA CONTINUAR";
        }else if (senha.length() < 10){
            return "A SENHA DEVE CONTER NO MINIMO 10 CARACTERES";
        }else if (senha.length() > 50){
            return "A SENHA DEVE TER NO MAXIMO 50 CARACTERES";
        }else if (!(senhaConfirma.equals(senha))){
            return "AS SENHAS NAO SAO IGUAIS";
        }else if(check.equals(false)){
            return "ACEITE OS TERMOS DE USO";
        }else{
            List<Usuario> usuarios = dao.usuarios();
            for(Usuario p:usuarios){
                if(p.getEmail().trim().equals(email) && p.getId() != usuario.getId()) {
                    return "ESTE EMAIL JA ESTA SENDO USADO";
                }
                if((p.getCpf().trim().equals(cpf)) && p.getId() != usuario.getId()){
                    return "ESTE CPF JA ESTA SENDO USADO";
                }
            }
            return"ok";
        }
    }

    public static Usuario logar(Context context, EditText txtEmail, EditText txtSenha){
        Usuario usuario = new Usuario();
        DAO pessoaDAO = new DAO(context.getApplicationContext());
        List<Usuario> pessoas = pessoaDAO.usuarios();
        String email = txtEmail.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();


        for(Usuario p:pessoas){
            if((p.getEmail().trim().equals(email)) && (p.getSenha().trim().equals(senha))){
                return p;
            }
        }
        return usuario;
    }

}
