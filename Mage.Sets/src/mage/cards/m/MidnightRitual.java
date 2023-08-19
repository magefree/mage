
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Skyler Sell
 */
public final class MidnightRitual extends CardImpl {

    public MidnightRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{B}");

        // Exile X target creature cards from your graveyard.
        // For each creature card exiled this way, create a 2/2 black Zombie creature token.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new MidnightRitualEffect());
        this.getSpellAbility().setTargetAdjuster(MidnightRitualAdjuster.instance);
    }

    private MidnightRitual(final MidnightRitual card) {
        super(card);
    }

    @Override
    public MidnightRitual copy() {
        return new MidnightRitual(this);
    }
}

enum MidnightRitualAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(ability.getManaCostsToPay().getX(), StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }
}

class MidnightRitualEffect extends OneShotEffect {

    public MidnightRitualEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile X target creature cards from your graveyard. For each creature card exiled this way, create a 2/2 black Zombie creature token";
    }

    public MidnightRitualEffect(final MidnightRitualEffect effect) {
        super(effect);
    }

    @Override
    public MidnightRitualEffect copy() {
        return new MidnightRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToExile = new CardsImpl(getTargetPointer().getTargets(game, source));
            controller.moveCards(cardsToExile, Zone.EXILED, source, game);
            if (!cardsToExile.isEmpty()) {
                game.getState().processAction(game);
                new ZombieToken().putOntoBattlefield(cardsToExile.size(), game, source, controller.getId());
            }
            return true;
        }
        return false;
    }
}
