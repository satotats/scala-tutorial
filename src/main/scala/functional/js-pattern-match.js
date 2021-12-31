/** pattern matchでの利用を前提としたlistデータ構造(代数的データ構造)
 *『関数型プログラミングの基礎』(立川察理, 2016)より */

// このlist定義において、実際に利用するlist型のデータは empty(空のlist) か cons(データ有りlist) のどちらか
// (listはあくまで抽象的な概念の定義。javaでいうとinterfaceや抽象クラスなどがこの役割を担う)

// listの利用時は、list型のうち「どの状態」のデータなのかを判定してから利用することになる
// (古いjsにはjavaのようなinterfaceが無く、interface経由で画一的に処理するような実装にはならない)

let list = {
    cons: (value, list) => {
        // listデータ型を扱う側に実装されている"cons(データ有りlist)"用の実装を呼び出すための関数
        return (pattern) => {
            return pattern.cons(value, list)
        }
    },
    empty: () => {
        // listデータ型を扱う側に実装されている"empty(空のlist)"用の実装を呼び出すための関数
        return (pattern) => {
            return pattern.empty()
        }
    }
}

/**
 * @param data (pattern) => Any
 * @param pattern データ構造を利用する側のオブジェクト
 * */
let match = (data, pattern) => {
    return data(pattern)
    /**
     * 上記のlistに限定すれば、上記は
     * list型中で宣言されている、patternを引数にとる関数を実行する関数ということになる
     *
     * dataにはlist型のデータ構造(cons / empty)のうち、具体的などちらかが代入される
     * たとえば空のlistをdataとして取る場合
     * { (pattern) => pattern.empty() }(pattern) なので、
     * 引数にpattern渡されたpatternに定義されている empty() 関数を呼び出す処理として機能する。
     * */
}
