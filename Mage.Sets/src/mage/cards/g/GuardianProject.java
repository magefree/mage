package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
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

        // Whenever a nontoken creature enters the battlefield under your control, if that creature does not have the same name as another creature you control or a creature card in your graveyard, draw a card.
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

    public static final FilterPermanent filterNonTokenControlledCreature = new FilterControlledCreaturePermanent();

    static {
        filterNonTokenControlledCreature.add(TokenPredicate.FALSE);
    }

    GuardianProjectTriggeredAbility() {
        super(new GuardianProjectEffect(null), filterNonTokenControlledCreature);
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
        if (!filter.match(permanent, controllerId, this, game)) {
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
        return "Whenever a nontoken creature enters the battlefield under your control, " +
                "if that creature does not have the same name as another creature you control " +
                "or a creature card in your graveyard, draw a card.";
    }

    // This is needed as checkInterveningIfClause can't access trigger event information
    static boolean checkCondition(Permanent permanent, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        if (!permanent.getName().isEmpty()) {
            FilterCard filterCard = new FilterCard();
            filterCard.add(new NamePredicate(permanent.getName()));
            filterCard.add(new OwnerIdPredicate(controllerId));
            if (player.getGraveyard().count(filterCard, game) > 0) {
                return false;
            }
        }
        FilterPermanent filterPermanent = new FilterCreaturePermanent();
        filterPermanent.add(new NamePredicate(permanent.getName()));
        filterPermanent.add(Predicates.not(new CardIdPredicate(permanent.getId())));
        filterPermanent.add(new ControllerIdPredicate(controllerId));
        if (!game.getBattlefield().getActivePermanents(filterPermanent, controllerId, game).isEmpty()) {
            return false;
        }
        return true;
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
        if (player == null) {
            return false;
        }
        if (GuardianProjectTriggeredAbility.checkCondition(
                mor.getPermanentOrLKIBattlefield(game), source.getControllerId(), game)
        ) {
            player.drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
