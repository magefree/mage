package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtrisOracleOfHalfTruths extends CardImpl {

    public AtrisOracleOfHalfTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Atris, Oracle of Half-Truths enters the battlefield, target opponent looks at the top three cards of your library and separates them into a face-down pile and a face-up pile. Put one pile into your hand and the other into your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AtrisOracleOfHalfTruthsEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private AtrisOracleOfHalfTruths(final AtrisOracleOfHalfTruths card) {
        super(card);
    }

    @Override
    public AtrisOracleOfHalfTruths copy() {
        return new AtrisOracleOfHalfTruths(this);
    }
}

class AtrisOracleOfHalfTruthsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards for the face-down pile");

    AtrisOracleOfHalfTruthsEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent looks at the top three cards of your library " +
                "and separates them into a face-down pile and a face-up pile. " +
                "Put one pile into your hand and the other into your graveyard";
    }

    private AtrisOracleOfHalfTruthsEffect(final AtrisOracleOfHalfTruthsEffect effect) {
        super(effect);
    }

    @Override
    public AtrisOracleOfHalfTruthsEffect copy() {
        return new AtrisOracleOfHalfTruthsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || targetOpponent == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));

        TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
        targetOpponent.choose(outcome, cards, target, game);
        Cards faceDownPile = new CardsImpl(target.getTargets());
        cards.removeAll(target.getTargets());
        controller.revealCards(sourceObject.getIdName() + " - cards in face-up pile", cards, game);
        game.informPlayers(targetOpponent.getLogName() + " puts " + faceDownPile.size() + " card(s) into the face-down pile");
        if (controller.chooseUse(outcome, "Choose a pile to put in your hand.", null,
                "Face-down", "Face-up", source, game)) {
            controller.moveCards(faceDownPile, Zone.HAND, source, game);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        } else {
            controller.moveCards(faceDownPile, Zone.GRAVEYARD, source, game);
            controller.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }
}
