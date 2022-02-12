package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class CrimsonHonorGuard extends CardImpl {

    public CrimsonHonorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each player's end step, Crimson Honor Guard deals 4 damage to that player unless they control a commander.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CrimsonHonorGuardEffect(), TargetController.EACH_PLAYER, false));
    }

    private CrimsonHonorGuard(final CrimsonHonorGuard card) {
        super(card);
    }

    @Override
    public CrimsonHonorGuard copy() {
        return new CrimsonHonorGuard(this);
    }
}

class CrimsonHonorGuardEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Commander");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public CrimsonHonorGuardEffect() {
        super(Outcome.Damage);
    }

    private CrimsonHonorGuardEffect(final CrimsonHonorGuardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int numCommanders = game.getBattlefield().getAllActivePermanents(filter, player.getId(), game).size();
            if (numCommanders == 0) {
                player.damage(4, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "{this} deals 4 damage to that player unless they control a commander";
    }

    @Override
    public CrimsonHonorGuardEffect copy() {
        return new CrimsonHonorGuardEffect(this);
    }
}
