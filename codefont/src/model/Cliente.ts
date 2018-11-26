import { TipoCliente } from './TipoCliente'

export class Cliente{

    private id: number;
    private nome: string;
    private tipoCliente: TipoCliente;

    public setId(id: number):void{
        this.id = id;
    }

    public getId():number{
        return this.id;
    }

    public setNome(nome: String):void{
        this.nome = nome;
    }

    public getNome():String{
        return this.nome;
    }

    public setTipoCliente(tipoCliente: TipoCliente):void{
        this.tipoCliente = tipoCliente;
    }

    public getTipoCliente():TipoCliente{
        return this.tipoCliente;
    }


}
