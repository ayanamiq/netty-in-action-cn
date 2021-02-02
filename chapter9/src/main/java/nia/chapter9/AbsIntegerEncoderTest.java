package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * 代码清单 9-4 测试 AbsIntegerEncoder
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        //创建一个 ByteBuf，并且写入 9 个负整数
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        //创建一个EmbeddedChannel，并安装一个要测试的AbsIntegerEncoder
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        //写入 ByteBuf，并断言调用 readOutbound()方法将会产生数据
        assertTrue(channel.writeOutbound(buf));
        //将该Channel标记为已完成状态
        assertTrue(channel.finish());
        // 读取所产生的消息，并断言它们包含了对应的绝对值
        for (int i = 1; i < 10; i++) {
            assertEquals(i, channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }
}
