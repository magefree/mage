package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EasterlingVanguard extends CardImpl {

    public EasterlingVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Easterling Vanguard dies, amass Orcs 1.
        this.addAbility(new DiesSourceTriggeredAbility(new AmassEffect(1, SubType.ORC)));
    }

    private EasterlingVanguard(final EasterlingVanguard card) {
        super(card);
    }

    @Override
    public EasterlingVanguard copy() {
        return new EasterlingVanguard(this);
    }
}
