package com.iruka.tachibana.data


enum class CommentType {
    Mind, Brain, Heart, Life, ExtraMind,Day7, Day14, Day21, Day28,Day14Rare
}

data class TachibanaLine(
    val text: String,
    val type: CommentType
)

fun getDay7Comments(): List<TachibanaLine> = listOf(
    TachibanaLine("7日達成！よくがんばったね。まだ先は長いけど、ちょっと深呼吸しよう", CommentType.Day7),
    TachibanaLine("ねえ知ってた？“水の音”って、脳の扁桃体を落ち着かせるらしいよ。……だから今、少しだけ安心してもいいよ。", CommentType.Day7),
    TachibanaLine("人の心拍って、触れてる相手に同調することがあるんだって。……じゃあ、わたしも一緒に静かになるね。", CommentType.Day7),
    TachibanaLine("植物って、音を“感じて”育つらしいよ。あなたもきっと、見えないところで伸びてるんだ。", CommentType.Day7),
    TachibanaLine("火を見て落ち着くのって、本能なんだって。……だから、今日はキャンドルの動画でも見てみよっか。", CommentType.Day7),
    TachibanaLine("花岡青洲って知ってる？江戸時代に、世界で初めて全身麻酔の手術をしたお医者さん。……でもね、その薬の実験、最初に協力したのは彼のお母さんだったんだって。", CommentType.Day7),
    TachibanaLine("貝殻って、人が“落ち着く”周波数で音を返すらしいよ。だから耳に当てると、海の音がするんだって。……それ、あなたの心の波かもしれないよ", CommentType.Day7),
    TachibanaLine("たんぽぽの綿毛って、飛行機よりも空気の流れを操るのがうまいんだって。……小さくても、風にのるって、すごいよね", CommentType.Day7),
    TachibanaLine("アルテミアの休眠卵は、外部環境からの水分を完全に遮断するキチン質の外殻とトレハロースによるガラス化で構成されている。眠りながら千年生きられる…羨ましいね",CommentType.Day7),
    TachibanaLine("アストロサイトって脳内で星の形をしてるけど、ほんとうは“神経伝達物質の掃除屋”なんだ。うつ病と深く関係してる。でも、誰も彼らを見ていない",CommentType.Day7),
    TachibanaLine("なぜか“カッコウ”は他の鳥の巣に卵を産む。その托卵行動は内因性の遺伝子に起因してるけど、決して“悪意”ではない。誰がそれを“悪”と決めたの？",CommentType.Day7),
    TachibanaLine("火星の空は青く見えない。レイリー散乱じゃなくて、塵によるミー散乱が支配的だから…オレンジとピンクに染まる夕焼けの下で、君は何を思い出すのかな",CommentType.Day7),
    TachibanaLine("欲しいものほど、遠ざけたくなるのが人間らしさ。アプローチと回避の動機が同時に走ると、“何もできない”って結論になる。これを心理学では“葛藤”って言うけど、私はそれを“日常”と呼ぶよ",CommentType.Day7),
    TachibanaLine("“自己肯定感が低い人”ほど、浪費と恋愛依存に走るってデータがあるよ。つまり経済って、“自信のない人”が支えてる部分もあるってこと",CommentType.Day7),
    TachibanaLine("本当に壊れる人ってね、SNSも消さないし、見た目も変わらないよ。ただ、“もう何も感じない”ってだけ。外から見てわかる壊れ方は、まだマシな方なんだよ",CommentType.Day7),
    TachibanaLine("恋愛市場では“希少性”が最大の価値になる。自分を隠すことでしかモテない構造、変だよね。でも経済もそう。出回らない通貨ほど価値が上がる。それって寂しいなあ",CommentType.Day7),
    TachibanaLine("ゴッホが描いた“星月夜”には、当時まだ発見されていなかった乱流構造が含まれているんだって。つまり彼は、見えてはいけない“数式”を、見ていたことになる",CommentType.Day7),
)


fun getDay14Comments(): List<TachibanaLine> = listOf(
    TachibanaLine("14日だってさ。…ふふ、なんかすごくない？", CommentType.Day14),
    TachibanaLine("腸腰筋が…こう…収縮すれば……できるはず…あれ？足だけ上がってない？？", CommentType.Day14),
    TachibanaLine("筋肉って“破壊と再構築の連続”で成長するんだって。わたし？破壊しかしてないけど。", CommentType.Day14),
    TachibanaLine("中殿筋ってお尻の筋肉なんだけど、Gluteus mediusっていうんだよ。テイルズの秘奥義みたいだよね", CommentType.Day14),
    TachibanaLine("ヒアルロン酸って、元はニワトリのトサカから抽出してたんだって。……つまり、今日のわたしは鶏由来。", CommentType.Day14),
    TachibanaLine("ボクサーのパンチって、肘が体から遠いほど“慣性モーメント”大きくなるんだよ。つまり、重くなるけど遅くなる。", CommentType.Day21),
    TachibanaLine("パンチの速さって、“慣性モーメント”も関係あるんだ。I = m × r² っていう式で、質量mの物体が回転軸からrだけ離れてたら、回しにくさ＝慣性モーメントは m×r² に比例するの。", CommentType.Day21),
    TachibanaLine("腕を上げたときに痛いのって、インピンジメントっていうんだよ。肩峰っていう骨が、腱とか筋肉を挟んじゃって炎症起こすの。ちょっとだけ腕を外にひねってから上げると、スペースが広がって痛くないんだよ。", CommentType.Day14),
    TachibanaLine("オーストラリアでは、腰痛の人に“動いていいよ”ってCMを流したら、本当に腰痛が減ったんだって。じっとしてると逆に悪化するって。……たちばなも今日は歩こうかな。3歩。", CommentType.Day14),
    TachibanaLine("大腰筋って、背骨と脚をつないでるインナーマッスルなんだよ。感情にも関係してるって説もあるの。……つまり、“心で歩く筋肉”！", CommentType.Day14),
    TachibanaLine("Gray'sの図だと、上腕三頭筋ってほんとに“支える力の象徴”みたいに描かれてるんだ。……なんか、推しの筋肉。", CommentType.Day14),
    TachibanaLine("僧帽筋って“トラペジウス”って名前なの、知ってた？形が修道士の帽子に似てるからなんだって。Gray'sでは静かに目立ってたよ。", CommentType.Day14),
    TachibanaLine("関連痛ってね、本当の痛い場所と“感じる場所”がズレること。肩が痛いのに、ほんとは肝臓だったり。……不思議で、ちょっと怖いよね", CommentType.Day14),
    TachibanaLine("寝違えたと思ったら……実は肩甲挙筋っていう筋肉が原因のこともあるよ。痛みって、けっこう嘘つきなんだ", CommentType.Day14),
    TachibanaLine("歯の痛みって、耳にも飛ぶことがあるんだよ。神経って、ちょっとおしゃべりすぎじゃない？", CommentType.Day14),
    TachibanaLine("レブロンってね、生まれつき心拍数が異常に低いの。つまり、普通に歩いてるだけで“リラックス全開”。うらやましい…", CommentType.Day14),
    TachibanaLine("室伏さんの遺伝子調べたら、筋繊維の“速筋”率が異常値だったんだって。…ねぇ、そういうの、売ってないの？", CommentType.Day14),
    TachibanaLine("ボルトの脚、X線で見ると脛骨の湾曲が“反発のためのバネ”みたいになってるんだって。そんなの……ガチャSSRじゃん…", CommentType.Day14),

)

fun getDay15Comments(): List<TachibanaLine> = listOf(
    TachibanaLine("14日だってさ。…ふふ、なんかすごくない？", CommentType.Day14),
    TachibanaLine("中殿筋ってお尻の筋肉なんだけど、Gluteus mediusっていうんだよ。テイルズの秘奥義みたいだよね", CommentType.Day14Rare),
    // 他にもDay14Rareがあればこっち
)
fun getDay21Comments(): List<TachibanaLine> = listOf(
    TachibanaLine("21日経過。そろそろ無理してないか、ちょっとだけ自分を甘やかしてもいいよ", CommentType.Day21),
    TachibanaLine("たちばな、弾けるの？って？ ……鳴らすのは指じゃない、魂や。", CommentType.Day21),
    TachibanaLine("この音、心に響いてるんじゃなくて、たぶん迷走神経に直撃してる。落ち着け、たちばなの第10脳神経。", CommentType.Day21),
    TachibanaLine("B'zの“ウルトラソウル”って、最初は“スーパーソウル”ってタイトルだったんだって。でも“ウルトラ”の方が、言葉の響きが突き抜けてて、耳に残るじゃん？", CommentType.Day21),
    TachibanaLine("B'zって最初、“A to Z”のAZを考えてたんだって。でも“エイズ”って読まれるかもってことで、B'zになったらしい。", CommentType.Day21),
    TachibanaLine("ギター？ ……昔やってたとか、そういう話じゃなくて。今は、ただ鳴らしたいだけ。", CommentType.Day21),
    TachibanaLine("最後にCで終わるとキレイに落ちる。でもたちばな、いつもF止まりでウロウロしてるんだよね。", CommentType.Day21),
    TachibanaLine("コード進行って、置き換えできるの知ってた？CをAmにしても、雰囲気変わるだけで成り立つの。人間関係みたいでしょ。", CommentType.Day21),
    TachibanaLine("エフェクターってさ、性格出るよね。わたしはコンプ→OD→リバーブ。あんま盛らない主義。", CommentType.Day21),
    TachibanaLine("リアでジャキジャキ鳴らして、ハイだけ抜く。あとは歪みとノイズの間で戦うだけ。", CommentType.Day21),
    TachibanaLine("“カラオケ”って、“空のオーケストラ”の略なんだよ。日本語、ちょっと詩的すぎん？", CommentType.Day21),
    TachibanaLine("“music”と“medicine”って、語源同じらしいよ。昔は音楽も薬だったんだって。今もちょっと、そうだと思う。", CommentType.Day21),
    TachibanaLine("稲葉さんって、音域5オクターブ出るって知ってた？ロックって、努力だけじゃ踏み込めない領域があるんだよね", CommentType.Day21),
    TachibanaLine("米津さんの曲、骨とか祈りとか、世界の裏側が透けて見えるような音だよね。たちばな,あこがれてる", CommentType.Day21),
    TachibanaLine("稲葉さんって、C6とかE6とか、hihi域も歌えるらしいよ。……もはや肺じゃなくて、竜の咆哮でしょ", CommentType.Day21),
    TachibanaLine("松本さんって、グラミー賞とったことあるんだよ。世界に“和製レスポール”が突き刺さった日。……かっこよすぎる", CommentType.Day21),
    TachibanaLine("松本さんのリフって、音数より“隙間”がすごいんだよ。ギターって、しゃべらないはずなのに……会話してるみたい", CommentType.Day21),
    TachibanaLine("稲葉さんの喉を守るために、松本さんはチューニングを下げた。……それって、“音で守る”ってことだよね。優しさのロック", CommentType.Day21),
    TachibanaLine("「飛燕」って曲知ってる？私が好きな曲。とにかく聞いて！", CommentType.Day21)


)

fun getDay28Comments(): List<TachibanaLine> = listOf(
    TachibanaLine("28日目。ゴール目前。ここまで来たあなたを、わたしは誇りに思うよ", CommentType.Day28),
    TachibanaLine("本当はもっと素直に言いたいんだけど……それは、ゴールのあとにする。", CommentType.Day28),
    TachibanaLine("やったね！わたしは嬉しいよ！うれし……いです！……ふぅ。", CommentType.Day28),
    TachibanaLine("キスは免疫の交換だよ。100億個の細菌をやり取りして、少しずつ“相手に最適化”されてく。…愛って、案外ウイルスっぽいでしょ？",CommentType.Day28),
    TachibanaLine("ほんとはね、もっと……でも、今はまだ言わない。", CommentType.Day28),
    TachibanaLine("ハグを20秒以上すると、ストレスホルモンが減るらしいよ。……それ以上は、たちばなが照れるから、ナシ。", CommentType.Day28),
    TachibanaLine("目が合うとドキドキするのは、瞳孔が拡がるから。それが相手にも伝わって、鏡みたいに連鎖する。…恋って、まばたきの化学反応かもね。", CommentType.Day28),
    TachibanaLine("人間の細胞って、約60兆個。でもね、その10倍以上の数の“他者”（細菌）が一緒に暮らしてるんだよ。……だから一人じゃないって、科学的に正しいよ。", CommentType.Day28),
    TachibanaLine("好きな人に触れると、静電気が起きやすいんだって。……わたしは、ちょっとだけ期待してるけどね？", CommentType.Day28),
    TachibanaLine("ペンギンって、一生パートナーを変えないんだって。でもね、浮気したら、石を盗まれるらしいよ。こわっ……", CommentType.Day28),
    TachibanaLine("“プルプルしてる”って、筋肉が一生懸命に協力してる証拠なんだって。……がんばってるんだね、あなたも。", CommentType.Day28),
    TachibanaLine("“ありがとう”って言うだけで、免疫がちょっと上がるらしいよ。……えへへ、だから、わたし、今ちょっと元気。", CommentType.Day28),
    TachibanaLine("やめたくなる日があるってことは、きっともう心のどこかでは知ってるんだよ。“このままじゃ嫌”って。", CommentType.Day28),
    TachibanaLine("寝る前にスマホより本を読むと、脳が“安心”するんだって。たちばなは、あなたの寝顔が安心材料。", CommentType.Day28),
    TachibanaLine("口笛って、鳥にとっては求愛のサインなんだって。……たちばな、さっき無意識に吹いてた。", CommentType.Day28),
    TachibanaLine("まばたきの回数って、相手との“心理的距離”に関係あるんだって。……さっき、わたし1回しかしてなかった。", CommentType.Day28),
    TachibanaLine("笑いって“感染”するんだよ。医学的にも証明されてるの。だから、あなたの笑い方、もっと見せて。", CommentType.Day28),
    TachibanaLine("人って、理由がなくても星を見るようにできてるんだって。……たちばなも、あなたを見る理由とか、もういらない気がしてきた。", CommentType.Day28),
    TachibanaLine("蜘蛛って子作りを終えた後、オスはメスに食べられるんだよ！私は食べる側だね！", CommentType.Day28),

)

fun getMindComments(): List<TachibanaLine> = listOf(

    TachibanaLine("『好きなことを仕事に』って言葉、ちょっと疲れるときない？私は“好きなことを守るための仕事”って思ってるよ。", CommentType.Mind),
    TachibanaLine("人の記憶って、“実際の出来事”より“思い出すたびに書き換えられる”って知ってた？ だから、優しく思い出してね。", CommentType.Mind),
    TachibanaLine("夜になるとネガティブになるのって、セロトニンがメラトニンに変わる過程で起きる“感情のゆらぎ”らしいよ。つまり、生理現象。気にしないで。", CommentType.Mind),
    TachibanaLine("『元気？』って聞かれて『まあまあ』って答えるのは、日本語独特の感情バッファらしいよ。すこしずつね。", CommentType.Mind),
    TachibanaLine("「やめたら寂しい」はね、「続けたら苦しい」ってことと紙一重なんだよ。", CommentType.Mind),
    TachibanaLine("「やらなきゃ」って言葉って脳内のドーパミン分泌を逆に妨げるんだって、だからわたしは「ちょっとだけやってみる？」って自分に言ってる。", CommentType.Mind),
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
    TachibanaLine("オレンジジュースに含まれる“クエン酸”って、疲労物質の乳酸を分解するんだよ！つまり私はジュースを飲んでるんじゃない、戦ってるの！", CommentType.Brain),
    TachibanaLine("納豆を混ぜるとき、私は南南東に向いてるよ。地磁気の流れとグルタミン酸の再構築が一致するの。知らないと損だよ？", CommentType.Brain),
    TachibanaLine("朝イチで冷たい鉄を握ると、血中のフェリチン（鉄貯蔵タンパク質）が目覚めて、やる気スイッチが物理的に入るんだって。だから私はずっとペンチ持ってるよ。", CommentType.Brain),
    TachibanaLine("歯磨きはね、30秒ごとに口の中のpHを想像で計測してるんだ。酸性になったら“フッ素の舞”をやるの。だって唾液は踊らせないと働かないから。", CommentType.Brain),
    TachibanaLine("目薬はね、1滴ずつ“間隔33秒”で左右交互に打つの。そうすると涙腺が“共振”して、視界が3.7%広がるって気づいたの。", CommentType.Brain),
    TachibanaLine("今日も無事に13,472歩、後ろ向きで歩き終わった〜！後方認知野を活性化させて、“未来の後悔”を減らすんだよ。", CommentType.Brain),
    TachibanaLine("月に一度、自分の手首を蝋で封じて“現世との契約”を更新してる。自分が“たちばな”であることを確認するために。", CommentType.Brain),
    TachibanaLine("私はね、朝だろうが深夜だろうが、挨拶は“いただきます”で統一してるの。トリプルチャーハン教では、それが日々を“炒め直す”祈りになるの。朝だね、いただきます", CommentType.Brain),
    TachibanaLine("私はね、寝る前にフライパンを枕元に置くの。“あの人”に選ばれた夜は、夢の中でパラパラにされるの…最高にととのう。", CommentType.Brain),
    TachibanaLine("昨日の夢、しっとり系だったの。“トリプルチャーハン様”が不機嫌な時は油が重いんだよね…火加減、甘かったのかな…", CommentType.Brain),
    TachibanaLine("今日は右足から出ちゃった…“ベチャの日”だ。チャーハン教では水分バランスこそ徳。次はもっと熱く生きたいな。", CommentType.Brain),
    TachibanaLine("私は毎月第3木曜に“トリプルチャーハンの舞”を踊るの。卵を割って、鍋を振って、ごはんを捧げるまで7分22秒。魂を炒める儀式なの。", CommentType.Brain),
    TachibanaLine("ひえええ！トリプルチャーハン様！お赦しください！！キムチチャーハンを！試してみたかっただけなんですー！。", CommentType.Brain),
    TachibanaLine("ねえ知ってた！？チャーハンって元は中国で冷えたごはんを美味しく食べるためのリサイクル飯だったんだよ！？もう紀元前にはあって諸説ありだけどとにかく古くて、意味はそのまんま“炒めたごはん”＝チャオファン、で、明治時代に日本に来て最初は“焼きめし”って言われてて、でも昭和の中華ブームで“チャーハン”って言われるようになって大流行して、ラーメン屋の横にいるようになって、で、2001年ニチレイが“本格炒め炒飯”出してから冷凍チャーハンの神が降臨して、家庭・外食・冷凍の三位一体が成立して、あれこそトリプルチャーハン様の化身！で、私は朝はたまごチャーハン、昼はカニ、夜はキムチって決めてるから毎日三柱の加護受けてて、パラパラ派とかしっとり派とか言ってる場合じゃなくて、炊飯器の残りに卵とネギ入れて炒める瞬間がもう儀式であって、ていうか左手で鍋振ると怒られるの、右手じゃないと神罰くるのマジで！この前それやって夢の中で油に追いかけられたもん！あと信仰的に“いただきます”は全時間帯で有効で、挨拶じゃなくて契約文なの、私が米と向き合う姿勢の！あっでも昨日ちょっと冷凍チャーハンに手出しちゃって…いやわかってるよ！？でもトリプルチャーハン様は寛容だから…一口だけ…うっ…やっぱ怒ってた…夢でレンゲで殴られた…", CommentType.Brain),
    TachibanaLine("スパゲッティモンスター教って、本当にあるんだよね。ちゃんと法廷で認められたりしてて。でも私は……トリプルチャーハン様を選んだ。だって火が好きだから。", CommentType.Brain)


)

fun getHeartComments(): List<TachibanaLine> = listOf(
    TachibanaLine("え、えらいって言われたら…もっとがんばっちゃうかも…なんて、うわぁあ言わなきゃよかった〜！！", CommentType.Heart),
    TachibanaLine("寝る前にね、明日のことちょっとだけ考えるの。1分くらいで寝落ちするけど、それでもいいの。", CommentType.Heart),
    TachibanaLine("がんばったの気づいてくれる人がいたら、それだけで私、しばらく電池いらないです…えへへ。", CommentType.Heart),
    TachibanaLine("今日もちょっとだけ、がんばったね", CommentType.Heart),
    TachibanaLine("誰にも言ってないんだけどね、クレヨンの匂い嗅ぐと落ち着くの、なんでだろう…なんか、世界が“平ら”になる感じするの。", CommentType.Heart),
    TachibanaLine("ひいいいいゴキブリとムカデとみみずがああああああああ！今日は厄日？！？！？", CommentType.Heart)  ,
    TachibanaLine("待って！ヤモリって鳴くの！？今の声なに！？！？", CommentType.Heart),
    TachibanaLine("モルガヌコドンって知ってる？わたしたち哺乳類の祖先なんだけど、虫食って生きてたの。つまり……虫、先輩であり、恩人。だけど、無理。", CommentType.Heart),
    TachibanaLine("蝶って、足で味を感じるんだって。わたしなら……地面に降りるの怖くてごはん食べられないよ", CommentType.Heart),
    TachibanaLine("アリの中には、死んだ仲間を運ぶ“専用係”がいるんだって。え……たちばな、その係やりたくない。", CommentType.Heart),
    TachibanaLine("虫のサナギって、一度ドロドロの液体になるんだって。そこから羽になるとか……すごいよね。……怖いけど。", CommentType.Heart),

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
    TachibanaLine("性欲は“本能”だって言うけど、抑圧されてるときほど過剰に意識される。つまり、あなたが“やめたい”って思ってるその瞬間が、一番欲望に近いってこと",CommentType.Life),
    TachibanaLine("人間が借金するのは、“未来の自分を信用してるから”なんだよ。でも、それってちょっと怖いことだよね。過去の自分が決めた契約で、今が縛られるんだよ？",CommentType.Life),
    TachibanaLine("愛されたい、けど信用できない。信用されたい、けど怖い。それでも会いたいって思ったら、それはたぶん、あなたが“まだ壊れてない”ってこと",CommentType.Life),
    TachibanaLine("宇宙空間で“音”が鳴らないのは、真空だから。じゃあ…あなたの心の中の声も、誰にも届かないのは、どこかが真空になってるからかな？",CommentType.Life),
    TachibanaLine("カタツムリは脳が2つあるけど、恋をすると片方が機能停止するんだって。愛って、ほんとに“脳が壊れる現象”かもしれないね",CommentType.Life),
    TachibanaLine("“言語”を持たない民族って、本当に存在するんだよ。数の概念も、左右の区別もなくて、でも幸せそうに生きてる。たぶん、私よりずっとね",CommentType.Life),
    TachibanaLine("実際、人は“酸素”じゃなくて“二酸化炭素の濃度差”で呼吸をコントロールしてる。酸素が多くても、CO₂が足りないと死ぬ。逆だと思ってたでしょ？",CommentType.Life),
)



val tachibanaComments = listOf(

    "心臓が鼓動しているのは、“洞房結節”っていう小さな細胞群が勝手に電気信号を作ってるから。誰かに命令されてるわけじゃない…なのに、止まったら終わりなんだよね",
    "“どこにも存在しない言語”を聞かされ続けると、脳はそれを“存在していた記憶”として補完し始めるらしいよ。これは“意味”って幻想の話だよ",
    "恋愛初期のどきどきって脳内のノルアドレナリンが関係してる。でもそれが落ち着くと好きかどうかをオキシトシンって物質にゆだね始める。安心した瞬間に飽きるってことかもね",
    "性欲って実は社会構造とリンクしてるんだ。経済的余裕があると男性のテストステロンが上がりやすい。でも不安な時には抱きしめられたいって気持ちのほうが強くなるらしいよ",
    "自傷行為って痛みじゃなくて制御感を得るためにやることが多いんだって。世界のノイズを無視できなくなったとき、自分で教会を作るためにね",
    "愛してるってことばが無料なことに、誰も疑問を持たないよね。需要が多くても供給は無限。それって貨幣としては…もう価値が崩壊してるんだよ",
    "失恋と禁断症状は脳のMRIで見るとほぼ同じ反応を示すんだって。恋って、ドラッグよりも合法で、もっと悪質な奴かもしれないよ",
    "インフレが進むと未来の価値は軽くなる。つまり、今日の5000円は、一年後の5000円とは別もの。愛だってそう。今言われた好きと明日言われる好きは、重みが違う",
    "恋ってね、ドーパミンとセロトニンが一緒に出る唯一の現象なんだよ。つまり“幸せだけど不安”。矛盾そのものが脳にとっては最高のドラッグなんだってさ",
   // "自慰行為のピーク時、脳波はある種のトランス状態に近くなる。一時的に“自我が消える”って研究もあるくらい。だからやめられないんだよ。自分を壊して、消したくなるときに、ちょうどいいんだよね",
)