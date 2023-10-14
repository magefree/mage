
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SliverToken;
import mage.players.Player;

/**
 *
 * @author cbt33, LevelX2 (Ogre Slumlord)
 */
public final class BroodSliver extends CardImpl {

    public BroodSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a player, its controller may create a 1/1 colorless Sliver creature token.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new BroodSliverEffect(),
                new FilterCreaturePermanent(SubType.SLIVER, "a Sliver"), false, SetTargetPointer.PLAYER, true));
    }

    private BroodSliver(final BroodSliver card) {
        super(card);
    }

    @Override
    public BroodSliver copy() {
        return new BroodSliver(this);
    }
}

class BroodSliverEffect extends OneShotEffect {

    public BroodSliverEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "its controller may create a 1/1 colorless Sliver creature token";
    }

    private BroodSliverEffect(final BroodSliverEffect effect) {
        super(effect);
    }

    @Override
    public BroodSliverEffect copy() {
        return new BroodSliverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player permanentController = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanentController != null) {
            if (permanentController.chooseUse(outcome, "create a 1/1 colorless Sliver creature token", source, game)) {
                return new SliverToken().putOntoBattlefield(1, game, source, permanentController.getId());
            }
            return true;
        }
        return false;
    }
}
