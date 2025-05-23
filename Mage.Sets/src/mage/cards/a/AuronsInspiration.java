package mage.cards.a;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuronsInspiration extends CardImpl {

    public AuronsInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Attacking creatures get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                2, 0, Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        ));

        // Flashback {2}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{W}{W}")));
    }

    private AuronsInspiration(final AuronsInspiration card) {
        super(card);
    }

    @Override
    public AuronsInspiration copy() {
        return new AuronsInspiration(this);
    }
}
