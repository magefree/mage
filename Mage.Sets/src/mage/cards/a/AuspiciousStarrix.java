package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourceMutatedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MutateAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuspiciousStarrix extends CardImpl {

    public AuspiciousStarrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELK);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Mutate {5}{G}
        this.addAbility(new MutateAbility(this, "{5}{G}"));

        // Whenever this creature mutates, exile cards from the top of your library until you exile X permanent cards, where X is the number of times this creature has mutated. Put those permanent cards onto the battlefield.
        this.addAbility(new MutatesSourceTriggeredAbility(new AuspiciousStarrixEffect()));
    }

    private AuspiciousStarrix(final AuspiciousStarrix card) {
        super(card);
    }

    @Override
    public AuspiciousStarrix copy() {
        return new AuspiciousStarrix(this);
    }
}

class AuspiciousStarrixEffect extends OneShotEffect {

    AuspiciousStarrixEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile X permanent cards, " +
                "where X is the number of times this creature has mutated. " +
                "Put those permanent cards onto the battlefield.";
    }

    private AuspiciousStarrixEffect(final AuspiciousStarrixEffect effect) {
        super(effect);
    }

    @Override
    public AuspiciousStarrixEffect copy() {
        return new AuspiciousStarrixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = SourceMutatedCount.instance.calculate(game, source, this);
        int count = 0;
        Cards toExile = new CardsImpl();
        Cards toBattlefield = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            if (card != null && card.isPermanent(game)) {
                toBattlefield.add(card);
                count++;
            }
            toExile.add(card);
            if (count >= xValue) {
                break;
            }
        }
        player.moveCards(toExile, Zone.EXILED, source, game);
        return player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
    }
}