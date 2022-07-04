package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.OonaQueenFaerieRogueToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OonaQueenOfTheFae extends CardImpl {

    public OonaQueenOfTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/B}{U/B}{U/B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {X}{UB}: Choose a color. Target opponent exiles the top X cards of their library. For each card of the chosen color exiled this way, create a 1/1 blue and black Faerie Rogue creature token with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OonaQueenOfTheFaeEffect(), new ManaCostsImpl<>("{X}{U/B}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private OonaQueenOfTheFae(final OonaQueenOfTheFae card) {
        super(card);
    }

    @Override
    public OonaQueenOfTheFae copy() {
        return new OonaQueenOfTheFae(this);
    }
}

class OonaQueenOfTheFaeEffect extends OneShotEffect {

    public OonaQueenOfTheFaeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose a color. Target opponent exiles the top X cards of their library. For each card of the chosen color exiled this way, create a 1/1 blue and black Faerie Rogue creature token with flying";
    }

    public OonaQueenOfTheFaeEffect(final OonaQueenOfTheFaeEffect effect) {
        super(effect);
    }

    @Override
    public OonaQueenOfTheFaeEffect copy() {
        return new OonaQueenOfTheFaeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        ChoiceColor choice = new ChoiceColor();
        if (controller == null || opponent == null || !controller.choose(outcome, choice, game)) {
            return false;
        }
        int cardsWithColor = 0;
        Cards cardsToExile = new CardsImpl();
        cardsToExile.addAll(opponent.getLibrary().getTopCards(game, source.getManaCostsToPay().getX()));

        for (Card card : cardsToExile.getCards(game)) {
            if (card != null && card.getColor(game).contains(choice.getColor())) {
                cardsWithColor++;
            }
        }
        controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        if (cardsWithColor > 0) {
            new CreateTokenEffect(new OonaQueenFaerieRogueToken(), cardsWithColor).apply(game, source);
        }
        game.informPlayers("Oona: " + cardsWithColor + " Token" + (cardsWithColor != 1 ? "s" : "") + " created");
        return true;
    }
}
