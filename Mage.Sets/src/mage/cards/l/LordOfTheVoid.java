package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public final class LordOfTheVoid extends CardImpl {

    public LordOfTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //Whenever Lord of the Void deals combat damage to a player, exile the top seven cards of that player's library, then put a creature card from among them onto the battlefield under your control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LordOfTheVoidEffect(), false, true));
    }

    private LordOfTheVoid(final LordOfTheVoid card) {
        super(card);
    }

    @Override
    public LordOfTheVoid copy() {
        return new LordOfTheVoid(this);
    }
}

class LordOfTheVoidEffect extends OneShotEffect {

    public LordOfTheVoidEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile the top seven cards of that player's library, then put a creature card from among them onto the battlefield under your control";
    }

    private LordOfTheVoidEffect(final LordOfTheVoidEffect effect) {
        super(effect);
    }

    @Override
    public LordOfTheVoidEffect copy() {
        return new LordOfTheVoidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        controller.moveCards(cards, Zone.EXILED, source, game);
        if (!cards.getCards(StaticFilters.FILTER_CARD_CREATURE, game).isEmpty()) {
            TargetCard target = new TargetCard(Zone.EXILED, StaticFilters.FILTER_CARD_CREATURE);
            if (controller.chooseTarget(outcome, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                }
            }
        }
        return true;
    }
}
