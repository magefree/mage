package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TrenchGorger extends CardImpl {

    public TrenchGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Trench Gorger enters the battlefield, you may search your library for any number of land cards, exile them, then shuffle your library. If you do, Trench Gorger's power and toughness each become equal to the number of cards exiled this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TrenchGorgerEffect(), true));

    }

    private TrenchGorger(final TrenchGorger card) {
        super(card);
    }

    @Override
    public TrenchGorger copy() {
        return new TrenchGorger(this);
    }
}

class TrenchGorgerEffect extends OneShotEffect {

    public TrenchGorgerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "search your library for any number of land cards, exile them, then shuffle. " +
                "If you do, {this} has base power and base toughness each equal to the number of cards exiled this way";
    }

    public TrenchGorgerEffect(final TrenchGorgerEffect effect) {
        super(effect);
    }

    @Override
    public TrenchGorgerEffect copy() {
        return new TrenchGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterLandCard("any number of land cards"));
            target.choose(outcome, controller.getId(), controller.getId(), source, game);
            int count = 0;
            for (UUID cardId : target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
                    count++;
                }
            }
            controller.shuffleLibrary(source, game);
            game.addEffect(new SetPowerToughnessSourceEffect(count, count, Duration.EndOfGame, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
