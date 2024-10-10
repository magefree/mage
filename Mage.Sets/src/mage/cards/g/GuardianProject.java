package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianProject extends CardImpl {

    public GuardianProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever a nontoken creature you control enters, if that creature does not have the same name as another creature you control or a creature card in your graveyard, draw a card.
        this.addAbility(new GuardianProjectTriggeredAbility());
    }

    private GuardianProject(final GuardianProject card) {
        super(card);
    }

    @Override
    public GuardianProject copy() {
        return new GuardianProject(this);
    }
}

class GuardianProjectTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    GuardianProjectTriggeredAbility() {
        super(new GuardianProjectEffect(null), StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN);
    }

    private GuardianProjectTriggeredAbility(final GuardianProjectTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GuardianProjectTriggeredAbility copy() {
        return new GuardianProjectTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null || !filter.match(permanent, controllerId, this, game)) {
            return false;
        }

        if (checkCondition(permanent, controllerId, game)) {
            this.getEffects().clear();
            this.addEffect(new GuardianProjectEffect(new MageObjectReference(permanent, game)));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control enters, " +
                "if it doesn't have the same name as another creature you control " +
                "or a creature card in your graveyard, draw a card.";
    }

    // This is needed as checkInterveningIfClause can't access trigger event information
    static boolean checkCondition(Permanent permanent, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        return player != null
                && player
                .getGraveyard()
                .getCards(game)
                .stream()
                .noneMatch(card -> card.sharesName(permanent, game))
                && game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, controllerId, game)
                .stream()
                .filter(p -> !p.getId().equals(permanent.getId()))
                .noneMatch(p -> p.sharesName(permanent, game));
    }
}

class GuardianProjectEffect extends OneShotEffect {

    private final MageObjectReference mor;

    GuardianProjectEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private GuardianProjectEffect(final GuardianProjectEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public GuardianProjectEffect copy() {
        return new GuardianProjectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = mor.getPermanentOrLKIBattlefield(game);
        if (player == null || permanent == null) {
            return false;
        }
        if (GuardianProjectTriggeredAbility.checkCondition(permanent, source.getControllerId(), game)) {
            player.drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
