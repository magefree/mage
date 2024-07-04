
package mage.cards.s;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatteredCrypt extends CardImpl {

    public ShatteredCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Return X target creature cards from your graveyard to your hand. You lose X life.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private ShatteredCrypt(final ShatteredCrypt card) {
        super(card);
    }

    @Override
    public ShatteredCrypt copy() {
        return new ShatteredCrypt(this);
    }
}
