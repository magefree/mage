package mage.cards.a;

import mage.abilities.costs.costadjusters.DiscardXCardsCostAdjuster;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AetherTide extends CardImpl {

    public AetherTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // As an additional cost to cast this spell, discard X creature cards.
        DiscardXCardsCostAdjuster.addAdjusterAndMessage(this, StaticFilters.FILTER_CARD_CREATURES);

        // Return X target creatures to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target creatures to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private AetherTide(final AetherTide card) {
        super(card);
    }

    @Override
    public AetherTide copy() {
        return new AetherTide(this);
    }
}
