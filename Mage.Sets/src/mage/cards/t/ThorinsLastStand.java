package mage.cards.t;

import java.util.UUID;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class ThorinsLastStand extends CardImpl {

    public ThorinsLastStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Choose one --
        // * Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));

        // * Destroy target artifact or enchantment. You gain 2 life.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        mode.addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addMode(mode);
    }

    private ThorinsLastStand(final ThorinsLastStand card) {
        super(card);
    }

    @Override
    public ThorinsLastStand copy() {
        return new ThorinsLastStand(this);
    }
}
