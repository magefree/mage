package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TasigurTheGoldenFang extends CardImpl {

    public TasigurTheGoldenFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());

        // {2}{G/U}{G/U}: Put the top two cards of your library into your graveyard, then return a nonland card of an opponent's choice from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new MillCardsControllerEffect(2), new ManaCostsImpl<>("{2}{G/U}{G/U}"));
        ability.addEffect(new TasigurTheGoldenFangEffect());
        this.addAbility(ability);
    }

    private TasigurTheGoldenFang(final TasigurTheGoldenFang card) {
        super(card);
    }

    @Override
    public TasigurTheGoldenFang copy() {
        return new TasigurTheGoldenFang(this);
    }
}

class TasigurTheGoldenFangEffect extends OneShotEffect {

    TasigurTheGoldenFangEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = ", then return a nonland card of an opponent's choice from your graveyard to your hand";
    }

    private TasigurTheGoldenFangEffect(final TasigurTheGoldenFangEffect effect) {
        super(effect);
    }

    @Override
    public TasigurTheGoldenFangEffect copy() {
        return new TasigurTheGoldenFangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID opponentId = null;
            if (game.getOpponents(controller.getId()).size() > 1) {
                Target target = new TargetOpponent(true);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    opponentId = target.getFirstTarget();
                }
            } else {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            }
            if (opponentId != null) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterNonlandCard filter = new FilterNonlandCard("nonland card from " + controller.getLogName() + " graveyard");
                    filter.add(new OwnerIdPredicate(controller.getId()));
                    Target target = new TargetCardInGraveyard(filter);
                    target.setNotTarget(true);
                    opponent.chooseTarget(outcome, target, source, game);
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
