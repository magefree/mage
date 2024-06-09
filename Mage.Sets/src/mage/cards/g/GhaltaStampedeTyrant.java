package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GhaltaStampedeTyrant extends CardImpl {

    public GhaltaStampedeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ghalta, Stampede Tyrant enters the battlefield, put any number of creature cards from your hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GhaltaStampedeTyrantEffect()));
    }

    private GhaltaStampedeTyrant(final GhaltaStampedeTyrant card) {
        super(card);
    }

    @Override
    public GhaltaStampedeTyrant copy() {
        return new GhaltaStampedeTyrant(this);
    }
}

// Very similar to Last March of the End
class GhaltaStampedeTyrantEffect extends OneShotEffect {

    GhaltaStampedeTyrantEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put any number of creature cards from your hand onto the battlefield";
    }

    private GhaltaStampedeTyrantEffect(final GhaltaStampedeTyrantEffect effect) {
        super(effect);
    }

    @Override
    public GhaltaStampedeTyrantEffect copy() {
        return new GhaltaStampedeTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES);
        player.choose(outcome, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}