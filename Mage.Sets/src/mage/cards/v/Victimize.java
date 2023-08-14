
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class Victimize extends CardImpl {

    public Victimize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose two target creature cards in your graveyard. Sacrifice a creature. If you do, return the chosen cards to the battlefield tapped.
        this.getSpellAbility().addEffect(new VictimizeEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(2, new FilterCreatureCard("creature cards in your graveyard")));
    }

    private Victimize(final Victimize card) {
        super(card);
    }

    @Override
    public Victimize copy() {
        return new Victimize(this);
    }
}

class VictimizeEffect extends OneShotEffect {

    VictimizeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose two target creature cards in your graveyard. Sacrifice a creature. If you do, return the chosen cards to the battlefield tapped";
    }

    VictimizeEffect(final VictimizeEffect effect) {
        super(effect);
    }

    @Override
    public VictimizeEffect copy() {
        return new VictimizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT);
            if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                game.getState().processAction(game); // To end effects of the sacrificed creature
                controller.moveCards(new CardsImpl(getTargetPointer().getTargets(game, source)).getCards(game),
                        Zone.BATTLEFIELD, source, game, true, false, false, null);
            }
            return true;
        }
        return false;
    }
}
