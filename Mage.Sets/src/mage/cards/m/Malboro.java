package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Malboro extends CardImpl {

    public Malboro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bad Breath -- When this creature enters, each opponent discards a card, loses 2 life, and exiles the top three cards of their library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        ability.addEffect(new LoseLifeOpponentsEffect(2).setText(", loses 2 life"));
        ability.addEffect(new MalboroEffect());
        this.addAbility(ability.withFlavorWord("Bad Breath"));

        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private Malboro(final Malboro card) {
        super(card);
    }

    @Override
    public Malboro copy() {
        return new Malboro(this);
    }
}

class MalboroEffect extends OneShotEffect {

    MalboroEffect() {
        super(Outcome.Benefit);
        staticText = ", and exiles the top three cards of their library";
    }

    private MalboroEffect(final MalboroEffect effect) {
        super(effect);
    }

    @Override
    public MalboroEffect copy() {
        return new MalboroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, 3), Zone.EXILED, source, game);
            }
        }
        return true;
    }
}
