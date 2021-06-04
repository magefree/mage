package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InevitableBetrayal extends CardImpl {

    public InevitableBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setBlue(true);

        // Suspend 3—{1}{U}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{U}{U}"), this));

        // Search target opponent's library for a creature card and put that card onto the battlefield under your control. Then that player shuffles.
        this.getSpellAbility().addEffect(new InevitableBetrayalEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private InevitableBetrayal(final InevitableBetrayal card) {
        super(card);
    }

    @Override
    public InevitableBetrayal copy() {
        return new InevitableBetrayal(this);
    }
}

class InevitableBetrayalEffect extends OneShotEffect {

    InevitableBetrayalEffect() {
        super(Outcome.Benefit);
        staticText = "search target opponent's library for a creature card and put that card " +
                "onto the battlefield under your control. Then that player shuffles";
    }

    private InevitableBetrayalEffect(final InevitableBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public InevitableBetrayalEffect copy() {
        return new InevitableBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE);
        controller.searchLibrary(target, source, game, player.getId());
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
