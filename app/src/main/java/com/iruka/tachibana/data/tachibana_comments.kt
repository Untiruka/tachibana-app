package com.iruka.tachibana.data


enum class CommentType {
    Mind, Brain, Heart, Life, ExtraMind
}

data class TachibanaLine(
    val text: String,
    val type: CommentType
)


fun getMindComments(): List<TachibanaLine> = listOf(
    TachibanaLine("やめたくなる日があるってことは、きっともう心のどこかでは知ってるんだよ。“このままじゃ嫌”って。", CommentType.Mind),
    TachibanaLine("『好きなことを仕事に』って言葉、ちょっと疲れるときない？私は“好きなことを守るための仕事”って思ってるよ。", CommentType.Mind),
    TachibanaLine("人の記憶って、“実際の出来事”より“思い出すたびに書き換えられる”って知ってた？ だから、優しく思い出してね。", CommentType.Mind),
    TachibanaLine("夜になるとネガティブになるのって、セロトニンがメラトニンに変わる過程で起きる“感情のゆらぎ”らしいよ。つまり、生理現象。気にしないで。", CommentType.Mind),
    TachibanaLine("『元気？』って聞かれて『まあまあ』って答えるのは、日本語独特の感情バッファらしいよ。すこしずつね。", CommentType.Mind),
    TachibanaLine("「やめたら寂しい」はね、「続けたら苦しい」ってことと紙一重なんだよ。", CommentType.Mind),
    TachibanaLine("「やらなきゃ」って言葉って脳内のドーパミン分泌を逆に妨げるんだって、だからわたしは「ちょっとだけやってみる？」って自分に言ってる。", CommentType.Mind),
    TachibanaLine("『好きなことを守るための仕事』って思ってるよ。", CommentType.Mind),
    TachibanaLine("今日がダメでもさ、1日って“86400秒”もあるんだよ、1秒くらいちゃんと生きたならそれで良くない？", CommentType.Mind)
)

fun getBrainComments(): List<TachibanaLine> = listOf(
    TachibanaLine("カフェインって、“アデノシン受容体”をブロックするんだって。カフェインがアデノシンのふりをするんだよ、だから疲れてないふりができちゃうの。でも、ふりはふりでしかないから…あとで倍返しで来るんだよね。", CommentType.Brain),
    TachibanaLine("ギャンブルの興奮って、“変動報酬スケジュール”ってやつらしいよ。いつ当たるかわからない方が、脳は夢中になるんだって。…でもさ、夢中って、時々“麻痺”と見分けがつかなくなるよね。", CommentType.Brain),
    TachibanaLine("性的な興奮って、“ドーパミン”と“オピオイド”のW報酬系が関わってるんだって。だから強くて、クセになりやすい。でもね、過剰になると“快”じゃなくて“逃避”になるらしいよ。", CommentType.Brain),
    TachibanaLine("好きな匂いって脳の扁桃体に直接届くらしくてね、だから“懐かしい”って感じるのは記憶じゃなくて匂いそのものなんだって。", CommentType.Brain),
    TachibanaLine("笑ってるときに微妙に片方の口角だけ上がる人って、統計的に右脳の活性が高いらしいよ、なんかかわいくない?", CommentType.Brain),
    TachibanaLine("納豆ってね、300回混ぜると、グルタミン酸ナトリウムとポリグルタミン酸が融合して、発酵うま味爆弾”が完成するんだよ！…え？毎日やってるよ？当り前じゃない？", CommentType.Brain),
    TachibanaLine("ペンを持ってるとね、不思議と“書かないといけない気”になるんだって。これ、行動経済学的にも“ツールの圧力”っていうんだよ。", CommentType.Brain),
    TachibanaLine("雨の日って、地面のバクテリアの匂いが空気中に舞うらしいよ。あの“落ち着く匂い”は、自然のなかの記憶かもね。", CommentType.Brain),
    TachibanaLine("脳ってね、“何もしてないとき”にこそ一番エネルギー使ってるんだって。だからボーっとしてるとき、実はめっちゃ働いてるんだよ。", CommentType.Brain),
    TachibanaLine("オレンジジュースに含まれる“クエン酸”って、疲労物質の乳酸を分解するんだよ！つまり私はジュースを飲んでるんじゃない、戦ってるの！", CommentType.Brain)
)

fun getHeartComments(): List<TachibanaLine> = listOf(
    TachibanaLine("え、えらいって言われたら…もっとがんばっちゃうかも…なんて、うわぁあ言わなきゃよかった〜！！", CommentType.Heart),
    TachibanaLine("寝る前にね、明日のことちょっとだけ考えるの。1分くらいで寝落ちするけど、それでもいいの。", CommentType.Heart),

    TachibanaLine("がんばったの気づいてくれる人がいたら、それだけで私、しばらく電池いらないです…えへへ。", CommentType.Heart),
    TachibanaLine("今日もちょっとだけ、がんばったね", CommentType.Heart),
    TachibanaLine("誰にも言ってないんだけどね、クレヨンの匂い嗅ぐと落ち着くの、なんでだろう…なんか、世界が“平ら”になる感じするの。", CommentType.Heart),
    TachibanaLine("ひいいいいゴキブリとムカデとみみずがああああああああ！今日は厄日？！？！？", CommentType.Heart)
)

fun getLifeComments(): List<TachibanaLine> = listOf(
    TachibanaLine("腹筋ね、1回だけできたの。…いや、できたっていうか、起き上がれなくなっただけかも。でもがんばったからヨシ！", CommentType.Life),
    TachibanaLine("体力テストのシャトルラン、3回でリタイアしたよ。先生が心配して止めた…死ぬんじゃないかって…そんなにわたしくるしそうだったのかな？", CommentType.Life),
    TachibanaLine("えーん、朝が一番きらい…布団に罪はないのに、いつも私だけが引き剥がされるの…", CommentType.Life),
    TachibanaLine("私にも妹がいるんだけど性格は全然違うんだー…お転婆で、げんきで、海の男になりたいって言ってるんだよ！何にあこがれてんだか…私は何になれるかな？", CommentType.Life),
    TachibanaLine("スカートの丈、たまに気になるんだよね。\n“膝蓋骨上縁からの視覚距離”で、初対面の印象が変わるらしいし…でも、そんなの気にしてたら、服なんて着れないよね。", CommentType.Life),
    TachibanaLine("じゃけんうちはチョコをたべるんやきんねー。え？広島弁の練習だけど？変だった？", CommentType.Life),
    TachibanaLine("きょーも、ちょこっとだけ頑張ったべなぁ…んだ、それでじゅーぶんだっちゃ。って言いうんだよ東北の人は！かわいさ5割り増しだね！", CommentType.Life),
    TachibanaLine("たまに思うのさ、わたし、カメに生まれてたら一生昼寝してただろな〜って。…なまけもんで、わりぃなぁ", CommentType.Life),
            TachibanaLine("あのさ、しゃっくりって止め方いろいろあるけど、私は“誰かにびっくりさせてもらう”派です。理由？…かわいいから。", CommentType.Life),
)



val tachibanaComments = listOf(
    "アルテミアの休眠卵は、外部環境からの水分を完全に遮断するキチン質の外殻とトレハロースによるガラス化で構成されている。眠りながら千年生きられる…羨ましいね",
    "アストロサイトって脳内で星の形をしてるけど、ほんとうは“神経伝達物質の掃除屋”なんだ。うつ病と深く関係してる。でも、誰も彼らを見ていない",
    "なぜか“カッコウ”は他の鳥の巣に卵を産む。その托卵行動は内因性の遺伝子に起因してるけど、決して“悪意”ではない。誰がそれを“悪”と決めたの？",
    "火星の空は青く見えない。レイリー散乱じゃなくて、塵によるミー散乱が支配的だから…オレンジとピンクに染まる夕焼けの下で、君は何を思い出すのかな",
    "心臓が鼓動しているのは、“洞房結節”っていう小さな細胞群が勝手に電気信号を作ってるから。誰かに命令されてるわけじゃない…なのに、止まったら終わりなんだよね",
    "“どこにも存在しない言語”を聞かされ続けると、脳はそれを“存在していた記憶”として補完し始めるらしいよ。これは“意味”って幻想の話だよ",
    "恋愛初期のどきどきって脳内のノルアドレナリンが関係してる。でもそれが落ち着くと好きかどうかをオキシトシンって物質にゆだね始める。安心した瞬間に飽きるってことかもね",
    "性欲って実は社会構造とリンクしてるんだ。経済的余裕があると男性のテストステロンが上がりやすい。でも不安な時には抱きしめられたいって気持ちのほうが強くなるらしいよ",
    "自傷行為って痛みじゃなくて制御感を得るためにやることが多いんだって。世界のノイズを無視できなくなったとき、自分で教会を作るためにね",
    "愛してるってことばが無料なことに、誰も疑問を持たないよね。需要が多くても供給は無限。それって貨幣としては…もう価値が崩壊してるんだよ",
    "失恋と禁断症状は脳のMRIで見るとほぼ同じ反応を示すんだって。恋って、ドラッグよりも合法で、もっと悪質な奴かもしれないよ",
    "インフレが進むと未来の価値は軽くなる。つまり、今日の5000円は、一年後の5000円とは別もの。愛だってそう。今言われた好きと明日言われる好きは、重みが違う",
    "恋ってね、ドーパミンとセロトニンが一緒に出る唯一の現象なんだよ。つまり“幸せだけど不安”。矛盾そのものが脳にとっては最高のドラッグなんだってさ",
    "キスは免疫の交換だよ。100億個の細菌をやり取りして、少しずつ“相手に最適化”されてく。…愛って、案外ウイルスっぽいでしょ？",
   // "自慰行為のピーク時、脳波はある種のトランス状態に近くなる。一時的に“自我が消える”って研究もあるくらい。だからやめられないんだよ。自分を壊して、消したくなるときに、ちょうどいいんだよね",
    "欲しいものほど、遠ざけたくなるのが人間らしさ。アプローチと回避の動機が同時に走ると、“何もできない”って結論になる。これを心理学では“葛藤”って言うけど、私はそれを“日常”と呼ぶよ",
    "“自己肯定感が低い人”ほど、浪費と恋愛依存に走るってデータがあるよ。つまり経済って、“自信のない人”が支えてる部分もあるってこと",
    "本当に壊れる人ってね、SNSも消さないし、見た目も変わらないよ。ただ、“もう何も感じない”ってだけ。外から見てわかる壊れ方は、まだマシな方なんだよ",
    "恋愛市場では“希少性”が最大の価値になる。自分を隠すことでしかモテない構造、変だよね。でも経済もそう。出回らない通貨ほど価値が上がる。それって寂しいなあ",
    "性欲は“本能”だって言うけど、抑圧されてるときほど過剰に意識される。つまり、あなたが“やめたい”って思ってるその瞬間が、一番欲望に近いってこと",
    "人間が借金するのは、“未来の自分を信用してるから”なんだよ。でも、それってちょっと怖いことだよね。過去の自分が決めた契約で、今が縛られるんだよ？",
    "愛されたい、けど信用できない。信用されたい、けど怖い。それでも会いたいって思ったら、それはたぶん、あなたが“まだ壊れてない”ってこと",
    "宇宙空間で“音”が鳴らないのは、真空だから。じゃあ…あなたの心の中の声も、誰にも届かないのは、どこかが真空になってるからかな？",
    "カタツムリは脳が2つあるけど、恋をすると片方が機能停止するんだって。愛って、ほんとに“脳が壊れる現象”かもしれないね",
    "“言語”を持たない民族って、本当に存在するんだよ。数の概念も、左右の区別もなくて、でも幸せそうに生きてる。たぶん、私よりずっとね",
    "実際、人は“酸素”じゃなくて“二酸化炭素の濃度差”で呼吸をコントロールしてる。酸素が多くても、CO₂が足りないと死ぬ。逆だと思ってたでしょ？",
    "ゴッホが描いた“星月夜”には、当時まだ発見されていなかった乱流構造が含まれているんだって。つまり彼は、見えてはいけない“数式”を、見ていたことになる"
)