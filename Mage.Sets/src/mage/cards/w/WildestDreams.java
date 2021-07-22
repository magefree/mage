
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WildestDreams extends CardImpl {

    public WildestDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Return X target cards from your graveyard to your hand.
        // Exile Wildest Dreams.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(WildestDreamsAdjuster.instance);
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private WildestDreams(final WildestDreams card) {
        super(card);
    }

    @Override
    public WildestDreams copy() {
        return new WildestDreams(this);
    }
}

enum WildestDreamsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(
                ability.getManaCostsToPay().getX(),
                StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD
        ));
    }
}