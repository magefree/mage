package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurlfistOak extends CardImpl {

    public BurlfistOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you draw a card, Burlfist Oak gets +2/+2 until end of turn.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), false
        ));
    }

    private BurlfistOak(final BurlfistOak card) {
        super(card);
    }

    @Override
    public BurlfistOak copy() {
        return new BurlfistOak(this);
    }
}
