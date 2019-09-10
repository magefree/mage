package mage.cards.q;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuestingBeast extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public QuestingBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Questing Beast can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));

        // Combat damage that would be dealt by creatures you control can't be prevented.
        this.addAbility(new SimpleStaticAbility(new QuestingBeastPreventionEffect()));

        // Whenever Questing Beast deals combat damage to an opponent, it deals that much damage to target planeswalker that player controls.
        this.addAbility(new QuestingBeastTriggeredAbility());
    }

    private QuestingBeast(final QuestingBeast card) {
        super(card);
    }

    @Override
    public QuestingBeast copy() {
        return new QuestingBeast(this);
    }
}

class QuestingBeastPreventionEffect extends ContinuousRuleModifyingEffectImpl {

    QuestingBeastPreventionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Combat damage that would be dealt by creatures you control can't be prevented.";
    }

    private QuestingBeastPreventionEffect(final QuestingBeastPreventionEffect effect) {
        super(effect);
    }

    @Override
    public QuestingBeastPreventionEffect copy() {
        return new QuestingBeastPreventionEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!((DamageEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isCreature()
                && permanent.isControlledBy(source.getControllerId());
    }
}

class QuestingBeastTriggeredAbility extends TriggeredAbilityImpl {

    QuestingBeastTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    private QuestingBeastTriggeredAbility(final QuestingBeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestingBeastTriggeredAbility copy() {
        return new QuestingBeastTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null
                || !event.getSourceId().equals(this.getSourceId())
                || !opponent.hasOpponent(this.getControllerId(), game)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DamageTargetEffect(event.getAmount()));
        FilterPermanent filter = new FilterPlaneswalkerPermanent("planeswalker " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to an opponent, " +
                "it deals that much damage to target planeswalker that player controls";
    }
}
