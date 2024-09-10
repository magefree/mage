package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtaliPrimalConqueror extends CardImpl {

    public EtaliPrimalConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.secondSideCardClazz = mage.cards.e.EtaliPrimalSickness.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Etali, Primal Conqueror enters the battlefield, each player exiles cards from the top of their library until they exile a nonland card. You may cast any number of spells from among the nonland cards exiled this way without paying their mana costs.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EtaliPrimalConquerorEffect()));

        // {9}{G/P}: Transform Etali. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{9}{G/P}")));
    }

    private EtaliPrimalConqueror(final EtaliPrimalConqueror card) {
        super(card);
    }

    @Override
    public EtaliPrimalConqueror copy() {
        return new EtaliPrimalConqueror(this);
    }
}

class EtaliPrimalConquerorEffect extends OneShotEffect {

    EtaliPrimalConquerorEffect() {
        super(Outcome.Benefit);
        staticText = "each player exiles cards from the top of their library until they exile a nonland card. " +
                "You may cast any number of spells from among the nonland cards exiled this way without paying their mana costs";
    }

    private EtaliPrimalConquerorEffect(final EtaliPrimalConquerorEffect effect) {
        super(effect);
    }

    @Override
    public EtaliPrimalConquerorEffect copy() {
        return new EtaliPrimalConquerorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            for (Card card : player.getLibrary().getCards(game)) {
                player.moveCards(card, Zone.EXILED, source, game);
                if (!card.isLand(game)) {
                    cards.add(card);
                    break;
                }
            }
        }
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
