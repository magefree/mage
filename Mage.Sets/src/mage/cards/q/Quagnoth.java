package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class Quagnoth extends CardImpl {

    public Quagnoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Shroud
        this.addAbility(ShroudAbility.getInstance());

        // When a spell or ability an opponent controls causes you to discard Quagnoth, return it to your hand.
        this.addAbility(new DiscardedByOpponentTriggeredAbility(new ReturnToHandSourceEffect()
                .setText("return it to your hand"), true));
    }

    private Quagnoth(final Quagnoth card) {
        super(card);
    }

    @Override
    public Quagnoth copy() {
        return new Quagnoth(this);
    }
}
