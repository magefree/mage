package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class TheCheeseStandsAlone extends CardImpl {

    public TheCheeseStandsAlone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // When you control no permanents other than The Cheese Stands Alone and have no cards in hand, you win the game.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new CheeseStandsAloneContinuousEffect());
        this.addAbility(ability);
    }

    private TheCheeseStandsAlone(final TheCheeseStandsAlone card) {
        super(card);
    }

    @Override
    public TheCheeseStandsAlone copy() {
        return new TheCheeseStandsAlone(this);
    }
}

class CheeseStandsAloneContinuousEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    private boolean wonAlready;

    static {
        filter.add(new NamePredicate("The Cheese Stands Alone"));
    }

    public CheeseStandsAloneContinuousEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, false, false);
        staticText = "When you control no permanents other than {this} and have no cards in hand, you win the game";
    }

    private CheeseStandsAloneContinuousEffect(final CheeseStandsAloneContinuousEffect effect) {
        super(effect);
        this.wonAlready = effect.wonAlready;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getHand().isEmpty()) {
                int numberPerms = new PermanentsOnBattlefieldCount(new FilterControlledPermanent()).calculate(game, source, this);
                if (numberPerms == 1) {
                    if (game.getBattlefield().containsControlled(filter, source, game, 1)) {
                        if (!wonAlready) {
                            wonAlready = true;
                            controller.won(game);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CheeseStandsAloneContinuousEffect copy() {
        return new CheeseStandsAloneContinuousEffect(this);
    }
}
