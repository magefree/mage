package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Defossilize extends CardImpl {

    public Defossilize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield. That creature explores, then it explores again.
        this.getSpellAbility().addEffect(new DefossilizeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private Defossilize(final Defossilize card) {
        super(card);
    }

    @Override
    public Defossilize copy() {
        return new Defossilize(this);
    }
}

class DefossilizeEffect extends OneShotEffect {

    DefossilizeEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield. " +
                "That creature explores, then it explores again";
    }

    private DefossilizeEffect(final DefossilizeEffect effect) {
        super(effect);
    }

    @Override
    public DefossilizeEffect copy() {
        return new DefossilizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        game.getState().processAction(game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        ExploreSourceEffect.explorePermanent(game, permanent.getId(), source, 2);
        return true;
    }
}
