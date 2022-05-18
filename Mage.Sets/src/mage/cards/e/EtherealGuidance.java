package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class EtherealGuidance extends CardImpl {

    public EtherealGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private EtherealGuidance(final EtherealGuidance card) {
        super(card);
    }

    @Override
    public EtherealGuidance copy() {
        return new EtherealGuidance(this);
    }
}
