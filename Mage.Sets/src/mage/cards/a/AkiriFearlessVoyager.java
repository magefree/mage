package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEquipmentPermanent;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkiriFearlessVoyager extends CardImpl {

    public AkiriFearlessVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you attack a player with one or more equipped creatures, draw a card.
        this.addAbility(new AkiriFearlessVoyagerTriggeredAbility());

        // {W}: You may unattach an Equipment from a creature you control. If you do, tap that creature and it gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new AkiriFearlessVoyagerEffect(), new ColoredManaCost(ColoredManaSymbol.W)
        ));
    }

    private AkiriFearlessVoyager(final AkiriFearlessVoyager card) {
        super(card);
    }

    @Override
    public AkiriFearlessVoyager copy() {
        return new AkiriFearlessVoyager(this);
    }
}

class AkiriFearlessVoyagerTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(EquippedPredicate.instance);
    }

    private final Set<UUID> attackedPlayerIds = new HashSet<>();

    AkiriFearlessVoyagerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private AkiriFearlessVoyagerTriggeredAbility(final AkiriFearlessVoyagerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AkiriFearlessVoyagerTriggeredAbility copy() {
        return new AkiriFearlessVoyagerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKERS_STEP_POST) {
            attackedPlayerIds.clear();
            return false;
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null
                    && creature.isControlledBy(controllerId)
                    && filter.match(creature, game)
                    && game.getPlayer(event.getTargetId()) != null
                    && !attackedPlayerIds.contains(event.getTargetId())) {
                attackedPlayerIds.add(event.getTargetId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you attack a player with one or more equipped creatures, draw a card.";
    }
}

class AkiriFearlessVoyagerEffect extends OneShotEffect {

    private static enum AkiriFearlessVoyagerPredicate implements ObjectPlayerPredicate<ObjectPlayer<Permanent>> {
        instance;

        @Override
        public boolean apply(ObjectPlayer<Permanent> input, Game game) {
            return game.getPermanent(input.getObject().getAttachedTo()) != null
                    && game.getControllerId(input.getObject().getAttachedTo()).equals(input.getPlayerId());
        }
    }

    private static final FilterPermanent filter
            = new FilterEquipmentPermanent("equipment attached to a creature you control");

    static {
        filter.add(AkiriFearlessVoyagerPredicate.instance);
    }

    AkiriFearlessVoyagerEffect() {
        super(Outcome.Benefit);
        staticText = "You may unattach an Equipment from a creature you control. " +
                "If you do, tap that creature and it gains indestructible until end of turn.";
    }

    private AkiriFearlessVoyagerEffect(final AkiriFearlessVoyagerEffect effect) {
        super(effect);
    }

    @Override
    public AkiriFearlessVoyagerEffect copy() {
        return new AkiriFearlessVoyagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(1, 1, filter, true);
        if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)
                || !player.chooseUse(outcome, "Unnattach an equipment from a creature you control?", source, game)) {
            return false;
        }
        player.choose(outcome, target, source.getSourceId(), game);
        Permanent equipment = game.getPermanent(target.getFirstTarget());
        if (equipment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(equipment.getAttachedTo());
        if (creature == null) {
            return false;
        }
        creature.removeAttachment(equipment.getId(), game);
        creature.tap(game);
        game.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(creature, game)), source);
        return true;
    }
}
