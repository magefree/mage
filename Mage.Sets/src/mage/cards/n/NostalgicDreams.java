
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class NostalgicDreams extends CardImpl {

    public NostalgicDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // As an additional cost to cast Nostalgic Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));
        // Return X target cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        // Exile Nostalgic Dreams.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

    }

    public NostalgicDreams(final NostalgicDreams card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = new GetXValue().calculate(game, ability, null);
            Target target = new TargetCardInYourGraveyard(xValue, StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD);
            ability.addTarget(target);
        }

    }

    @Override
    public NostalgicDreams copy() {
        return new NostalgicDreams(this);
    }
}
