package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.LightningRagerToken;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class RiteOfTheRagingStorm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures named Lightning Rager");

    static {
        filter.add(new NamePredicate("Lightning Rager"));
    }

    public RiteOfTheRagingStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Creatures named Lightning Rager can't attack you or planeswalkers you control.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of each player's upkeep, that player creates a 5/1 red Elemental creature token named Lightning Rager.
        // It has trample, haste, and "At the beginning of the end step, sacrifice this creature."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RiteOfTheRagingStormEffect(), TargetController.ANY, false));
    }

    private RiteOfTheRagingStorm(final RiteOfTheRagingStorm card) {
        super(card);
    }

    @Override
    public RiteOfTheRagingStorm copy() {
        return new RiteOfTheRagingStorm(this);
    }
}

class RiteOfTheRagingStormEffect extends OneShotEffect {

    RiteOfTheRagingStormEffect() {
        super(Outcome.Sacrifice);
        staticText = "that player creates a 5/1 red Elemental creature token named Lightning Rager. "
                + "It has trample, haste, and \"At the beginning of the end step, sacrifice this creature.\"";
    }

    private RiteOfTheRagingStormEffect(final RiteOfTheRagingStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            return new LightningRagerToken().putOntoBattlefield(1, game, source, player.getId());
        }
        return false;
    }

    @Override
    public RiteOfTheRagingStormEffect copy() {
        return new RiteOfTheRagingStormEffect(this);
    }
}
