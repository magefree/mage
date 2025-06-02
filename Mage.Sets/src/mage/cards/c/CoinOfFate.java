package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author balazskristof
 */
public final class CoinOfFate extends CardImpl {

    public CoinOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // When this artifact enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {3}{W},{T}, Exile two creature cards from your graveyard, Sacrifice this artifact: An opponent chooses one of the exiled cards. You put that card on the bottom of your library and return the other to the battlefield tapped. You become the monarch.
        Ability ability = new SimpleActivatedAbility(new CoinOfFateEffect(), new ManaCostsImpl<>("{3}{W}")).addHint(MonarchHint.instance);
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARD_CREATURES), true));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new BecomesMonarchSourceEffect());
        this.addAbility(ability);
    }

    private CoinOfFate(final CoinOfFate card) {
        super(card);
    }

    @Override
    public CoinOfFate copy() {
        return new CoinOfFate(this);
    }
}

class CoinOfFateEffect extends OneShotEffect {

    public CoinOfFateEffect() {
        super(Outcome.Benefit);
        staticText = "An opponent chooses one of the exiled cards. "
                + "You put that card on the bottom of your library and return the other to the battlefield tapped";
    }

    private CoinOfFateEffect(final CoinOfFateEffect effect) {
        super(effect);
    }

    @Override
    public CoinOfFateEffect copy() {
        return new CoinOfFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (cards.isEmpty()) {
            return false;
        }
        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            TargetOpponent targetOpponent = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
            opponent = game.getPlayer(targetOpponent.getFirstTarget());
        }
        if (opponent == null) {
            return false;
        }
        TargetCard targetCard = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_CREATURE);
        targetCard.withChooseHint("card to put on the bottom of opponent's library, the other is put onto the battlefield tapped");
        opponent.chooseTarget(Outcome.Benefit, cards, targetCard, source, game);
        Card cardToLibrary = game.getCard(targetCard.getFirstTarget());
        if (cardToLibrary != null) {
            controller.moveCardToLibraryWithInfo(cardToLibrary, source, game, Zone.EXILED, false, true);
            cards.remove(cardToLibrary);
        }
        if (!cards.isEmpty()) {
            controller.moveCards(game.getCard(cards.iterator().next()), Zone.BATTLEFIELD, source, game, true, false, true, null);
        }
        return true;
    }
}
