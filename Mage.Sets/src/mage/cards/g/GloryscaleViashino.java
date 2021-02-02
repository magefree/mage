
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class GloryscaleViashino extends CardImpl {

    public GloryscaleViashino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a multicolored spell, Gloryscale Viashino gets +3/+3 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(3, 3, Duration.EndOfTurn), StaticFilters.FILTER_SPELL_A_MULTICOLORED, false));
    }

    private GloryscaleViashino(final GloryscaleViashino card) {
        super(card);
    }

    @Override
    public GloryscaleViashino copy() {
        return new GloryscaleViashino(this);
    }
}
