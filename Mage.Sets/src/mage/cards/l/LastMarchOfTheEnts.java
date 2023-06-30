package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.dynamicvalue.common.GreatestToughnessAmongControlledCreaturesValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LastMarchOfTheEnts extends CardImpl {

    public LastMarchOfTheEnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}{G}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Draw cards equal to the greatest toughness among creatures you control, then put any number of creature cards from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(
                GreatestToughnessAmongControlledCreaturesValue.instance
        ).setText("draw cards equal to the greatest toughness among creatures you control"));
        this.getSpellAbility().addEffect(new LastMarchOfTheEntsEffect());
    }

    private LastMarchOfTheEnts(final LastMarchOfTheEnts card) {
        super(card);
    }

    @Override
    public LastMarchOfTheEnts copy() {
        return new LastMarchOfTheEnts(this);
    }
}

class LastMarchOfTheEntsEffect extends OneShotEffect {

    LastMarchOfTheEntsEffect() {
        super(Outcome.Benefit);
        staticText = ", then put any number of creature cards from your hand onto the battlefield";
    }

    private LastMarchOfTheEntsEffect(final LastMarchOfTheEntsEffect effect) {
        super(effect);
    }

    @Override
    public LastMarchOfTheEntsEffect copy() {
        return new LastMarchOfTheEntsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES);
        player.choose(outcome, player.getHand(), target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}
