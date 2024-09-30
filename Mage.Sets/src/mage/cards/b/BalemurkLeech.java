package mage.cards.b;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BalemurkLeech extends CardImpl {

    public BalemurkLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, each opponent loses 1 life.
        this.addAbility(new EerieAbility(new LoseLifeOpponentsEffect(1)));
    }

    private BalemurkLeech(final BalemurkLeech card) {
        super(card);
    }

    @Override
    public BalemurkLeech copy() {
        return new BalemurkLeech(this);
    }
}
