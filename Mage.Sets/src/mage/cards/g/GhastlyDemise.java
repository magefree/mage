package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class GhastlyDemise extends CardImpl {

    public GhastlyDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Destroy target nonblack creature if its toughness is less than or equal to the number of cards in your graveyard.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new GhastlyDemiseEffect());
    }

    private GhastlyDemise(final GhastlyDemise card) {
        super(card);
    }

    @Override
    public GhastlyDemise copy() {
        return new GhastlyDemise(this);
    }
}

class GhastlyDemiseEffect extends OneShotEffect {

    GhastlyDemiseEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy target nonblack creature if its toughness " +
                "is less than or equal to the number of cards in your graveyard";
    }

    private GhastlyDemiseEffect(final GhastlyDemiseEffect effect) {
        super(effect);
    }

    @Override
    public GhastlyDemiseEffect copy() {
        return new GhastlyDemiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        return permanent != null && player != null
                && permanent.getToughness().getValue() <= player.getGraveyard().size()
                && permanent.destroy(source, game);
    }
}
