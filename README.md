## Java文字冒险
通过解析XML文件构建初始地图，物品，人物与他们之间的交互；随后客户端键入命令，服务端返回结果进行游戏

### 示例初始化文件
文件中定义了物品与对应的操作关键字，地图之间的相对位置，人物的关系。通过检测关键字进行游戏
<?xml version="1.0" encoding="UTF-8"?>
<actions>
    <action>
        <triggers>
            <keyphrase>open</keyphrase>
            <keyphrase>unlock</keyphrase>
        </triggers>
        <subjects>
            <entity>trapdoor</entity>
            <entity>key</entity>
        </subjects>
        <consumed>
            <entity>key</entity>
        </consumed>
        <produced>
            <entity>cellar</entity>
        </produced>
        <narration>You unlock the door and see steps leading down into a cellar</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>chop</keyphrase>
            <keyphrase>cut</keyphrase>
            <keyphrase>cut down</keyphrase>
        </triggers>
        <subjects>
            <entity>tree</entity>
            <entity>axe</entity>
        </subjects>
        <consumed>
            <entity>tree</entity>
        </consumed>
        <produced>
            <entity>log</entity>
        </produced>
        <narration>You cut down the tree with the axe</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>drink</keyphrase>
        </triggers>
        <subjects>
            <entity>potion</entity>
        </subjects>
        <consumed>
            <entity>potion</entity>
        </consumed>
        <produced>
            <entity>health</entity>
        </produced>
        <narration>You drink the potion and your health improves</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>fight</keyphrase>
            <keyphrase>hit</keyphrase>
            <keyphrase>attack</keyphrase>
        </triggers>
        <subjects>
            <entity>elf</entity>
        </subjects>
        <consumed>
            <entity>health</entity>
        </consumed>
        <produced>
        </produced>
        <narration>You attack the elf, but he fights back and you lose some health</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>pay</keyphrase>
        </triggers>
        <subjects>
            <entity>elf</entity>
            <entity>coin</entity>
        </subjects>
        <consumed>
            <entity>coin</entity>
        </consumed>
        <produced>
            <entity>shovel</entity>
        </produced>
        <narration>You pay the elf your silver coin and he produces a shovel</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>bridge</keyphrase>
        </triggers>
        <subjects>
            <entity>log</entity>
            <entity>river</entity>
        </subjects>
        <consumed>
            <entity>log</entity>
        </consumed>
        <produced>
            <entity>clearing</entity>
        </produced>
        <narration>You bridge the river with the log and can now reach the other side</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>dig</keyphrase>
        </triggers>
        <subjects>
            <entity>ground</entity>
            <entity>shovel</entity>
        </subjects>
        <consumed>
            <entity>ground</entity>
        </consumed>
        <produced>
            <entity>hole</entity>
            <entity>gold</entity>
        </produced>
        <narration>You dig into the soft ground and unearth a pot of gold !!!</narration>
    </action>

    <action>
        <triggers>
            <keyphrase>blow</keyphrase>
        </triggers>
        <subjects>
            <entity>horn</entity>
        </subjects>
        <consumed>
        </consumed>
        <produced>
            <entity>lumberjack</entity>
        </produced>
        <narration>You blow the horn and as if by magic, a lumberjack appears !</narration>
    </action>

</actions>

