package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JadelightSpelunker extends CardImpl {

    public JadelightSpelunker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Jadelight Spelunker enters the battlefield, it explores X times.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ExploreSourceEffect(ManacostVariableValue.ETB, false, "it")
                        .setText("it explores X times. <i>(To have it explore, reveal the top card of your library. "
                                + "Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on that creature, "
                                + "then put the card back or put it into your graveyard.)</i>")
        ));
    }

    private JadelightSpelunker(final JadelightSpelunker card) {
        super(card);
    }

    @Override
    public JadelightSpelunker copy() {
        return new JadelightSpelunker(this);
    }
}
