
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RendingVines extends CardImpl {

    public RendingVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");
        this.subtype.add(SubType.ARCANE);

        // Destroy target artifact or enchantment if its converted mana cost is less than or equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new RendingVinesEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RendingVines(final RendingVines card) {
        super(card);
    }

    @Override
    public RendingVines copy() {
        return new RendingVines(this);
    }
}

class RendingVinesEffect extends OneShotEffect {

    public RendingVinesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment if its mana value is less than or equal to the number of cards in your hand";
    }

    private RendingVinesEffect(final RendingVinesEffect effect) {
        super(effect);
    }

    @Override
    public RendingVinesEffect copy() {
        return new RendingVinesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent.getManaValue() <= controller.getHand().size()) {
                return permanent.destroy(source, game, false);
            }
        }
        return false;
    }
}
