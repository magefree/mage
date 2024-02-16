package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetStackObject;

/**
 * @author Addictiveme
 */
public final class MirrorshellCrab extends CardImpl {

    public MirrorshellCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);
        
        // Ward {3}
        this.addAbility(new WardAbility(new GenericManaCost(3)));
        
        // Channel - {2}{U}, Discard Mirrorshell Crab:
        // Counter target spell or ability unless its controller pays {3}
        Ability channelAbility = new ChannelAbility("{2}{U}", new CounterUnlessPaysEffect(new GenericManaCost(3))
        		.setText("Counter target spell or ability unless its controller pays {3}"));
        channelAbility.addTarget(new TargetStackObject());
        this.addAbility(channelAbility);
    }

    private MirrorshellCrab(final MirrorshellCrab card) {
        super(card);
    }

    @Override
    public MirrorshellCrab copy() {
        return new MirrorshellCrab(this);
    }
}