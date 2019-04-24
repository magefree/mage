
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
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
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInYourGraveyard(xValue, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD));
        }
    }

    public WildestDreams(final WildestDreams card) {
        super(card);
    }

    @Override
    public WildestDreams copy() {
        return new WildestDreams(this);
    }
}
