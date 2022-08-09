package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DhalsimPliablePacifist extends CardImpl {

    private static final Condition condition = new InvertCondition(SourceAttackingCondition.instance);
    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with reach");

    static {
        filter.add(new AbilityPredicate(ReachAbility.class));
    }

    public DhalsimPliablePacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Teleport—Dhalsim, Pliable Pacifist has hexproof unless he's attacking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                condition, "{this} has hexproof unless he's attacking"
        )).withFlavorWord("Teleport"));

        // Whenever a creature you control with reach attacks, untap it and it can't be blocked by creatures with greater power this combat.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new DhalsimPliablePacifistEffect(), false, filter, true
        ));

        // Fierce Punch—Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DhalsimPliablePacifistTriggeredAbility());
    }

    private DhalsimPliablePacifist(final DhalsimPliablePacifist card) {
        super(card);
    }

    @Override
    public DhalsimPliablePacifist copy() {
        return new DhalsimPliablePacifist(this);
    }
}

class DhalsimPliablePacifistEffect extends OneShotEffect {

    DhalsimPliablePacifistEffect() {
        super(Outcome.Benefit);
        staticText = "untap it and it can't be blocked by creatures with greater power this combat";
    }

    private DhalsimPliablePacifistEffect(final DhalsimPliablePacifistEffect effect) {
        super(effect);
    }

    @Override
    public DhalsimPliablePacifistEffect copy() {
        return new DhalsimPliablePacifistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.untap(game);
        game.addEffect(new DhalsimPliablePacifistBlockEffect(permanent, game), source);
        return true;
    }
}

class DhalsimPliablePacifistBlockEffect extends RestrictionEffect {

    private final MageObjectReference mor;

    DhalsimPliablePacifistBlockEffect(Permanent permanent, Game game) {
        super(Duration.EndOfTurn);
        this.mor = new MageObjectReference(permanent, game);
    }

    private DhalsimPliablePacifistBlockEffect(final DhalsimPliablePacifistBlockEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public DhalsimPliablePacifistBlockEffect copy() {
        return new DhalsimPliablePacifistBlockEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attacker = mor.getPermanent(game);
        if (attacker == null) {
            discard();
            return false;
        }
        return permanent.getPower().getValue() > attacker.getPower().getValue();
    }
}

class DhalsimPliablePacifistTriggeredAbility extends TriggeredAbilityImpl {

    private final Set<UUID> damagedPlayerIds = new HashSet<>();

    DhalsimPliablePacifistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.withFlavorWord("Fierce Punch");
        setTriggerPhrase("Whenever one or more creatures you control deal combat damage to a player, ");
    }

    private DhalsimPliablePacifistTriggeredAbility(final DhalsimPliablePacifistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DhalsimPliablePacifistTriggeredAbility copy() {
        return new DhalsimPliablePacifistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY ||
                (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId()))) {
            damagedPlayerIds.clear();
            return false;
        }
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER) {
            return false;
        }
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (!damageEvent.isCombatDamage()
                || permanent == null
                || !permanent.isControlledBy(this.getControllerId())
                || !permanent.isCreature(game) ||
                damagedPlayerIds.contains(event.getPlayerId())) {
            return false;
        }
        damagedPlayerIds.add(event.getPlayerId());
        return true;
    }
}
