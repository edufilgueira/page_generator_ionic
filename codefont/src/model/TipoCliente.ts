
export class TipoCliente{

    private id: number;
    private descricao: string;

    public setId(id: number):void{
        this.id = id;
    }

    public getId():number{
        return this.id;
    }

    public setDescricao(descricao: String):void{
        this.descricao = descricao;
    }

    public getDescricao():String{
        return this.descricao;
    }


}
