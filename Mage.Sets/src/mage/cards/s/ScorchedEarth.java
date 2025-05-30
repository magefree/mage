package mage.cards.s;

import mage.abilities.costs.costadjusters.DiscardXCardsCostAdjuster;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ScorchedEarth extends CardImpl {

    public ScorchedEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // As an additional cost to cast this spell, discard X land cards.
        DiscardXCardsCostAdjuster.addAdjusterAndMessage(this, StaticFilters.FILTER_CARD_LANDS);

        // Destroy X target lands.
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy X target lands");
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addHint(CardsInControllerHandCount.LANDS.getHint());
    }

    private ScorchedEarth(final ScorchedEarth card) {
        super(card);
    }

    @Override
    public ScorchedEarth copy() {
        return new ScorchedEarth(this);
    }
}