package mage.cards.c;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoordinatedClobbering extends CardImpl {

    public CoordinatedClobbering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Tap one or two target untapped creatures you control. They each deal damage equal to their power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(
                1, 2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
        ).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent().setTargetTag(3));
    }

    private CoordinatedClobbering(final CoordinatedClobbering card) {
        super(card);
    }

    @Override
    public CoordinatedClobbering copy() {
        return new CoordinatedClobbering(this);
    }
}
// flame on!
