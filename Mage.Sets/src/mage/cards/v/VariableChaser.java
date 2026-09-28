package mage.cards.v;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class VariableChaser extends PrepareCard {

    public VariableChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}", "Arc of Fortune", new CardType[]{CardType.SORCERY}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Arc of Fortune
        // Sorcery {2}{U}
        // Each player may discard their hand and draw seven cards.
        this.getSpellCard().getSpellAbility().addEffect(new ArcOfFortuneEffect());
    }

    private VariableChaser(final VariableChaser card) {
        super(card);
    }

    @Override
    public VariableChaser copy() {
        return new VariableChaser(this);
    }
}


class ArcOfFortuneEffect extends OneShotEffect {

    ArcOfFortuneEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard their hand and draw seven cards";
    }

    private ArcOfFortuneEffect(final ArcOfFortuneEffect effect) {
        super(effect);
    }

    @Override
    public ArcOfFortuneEffect copy() {
        return new ArcOfFortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> wheelers = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.chooseUse(
                    Outcome.DrawCard, "Discard your hand and draw seven?", source, game
            )) {
                game.informPlayers(player.getName() + " chooses to discard their hand and draw seven");
                wheelers.add(player);
            }
        }
        for (Player player : wheelers) {
            player.discard(player.getHand(), false, source, game);
            player.drawCards(7, source, game);
        }
        return true;
    }
}
