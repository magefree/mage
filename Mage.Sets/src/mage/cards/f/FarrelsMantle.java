package mage.cards.f;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FarrelsMantle extends CardImpl {

    public FarrelsMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks and isn't blocked, its controller may have it deal damage equal to its power plus 2 to another target creature. If that player does, the attacking creature assigns no combat damage this turn.
        this.addAbility(new FarrelsMantleTriggeredAbility());
    }

    private FarrelsMantle(final FarrelsMantle card) {
        super(card);
    }

    @Override
    public FarrelsMantle copy() {
        return new FarrelsMantle(this);
    }
}

class FarrelsMantleTriggeredAbility extends TriggeredAbilityImpl {

    FarrelsMantleTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private FarrelsMantleTriggeredAbility(final FarrelsMantleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FarrelsMantleTriggeredAbility copy() {
        return new FarrelsMantleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = getSourcePermanentOrLKI(game);
        if (aura == null
                || !event.getTargetId().equals(aura.getAttachedTo())
                || game.getPermanent(aura.getAttachedTo()) == null) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(event.getTargetId(), game);
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new MageObjectReferencePredicate(mor)));
        TargetPermanent target = new TargetPermanent(filter);
        target.setTargetController(game.getControllerId(event.getTargetId()));
        this.getTargets().clear();
        this.addTarget(target);
        this.getEffects().clear();
        this.addEffect(new FarrelsMantleEffect(mor));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks and isn't blocked, its controller may have it deal damage " +
                "equal to its power plus 2 to another target creature. If that player does, " +
                "the attacking creature assigns no combat damage this turn.";
    }
}

class FarrelsMantleEffect extends OneShotEffect {

    private final MageObjectReference mor;

    FarrelsMantleEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private FarrelsMantleEffect(final FarrelsMantleEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public FarrelsMantleEffect copy() {
        return new FarrelsMantleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = mor.getPermanent(game);
        Permanent targeted = game.getPermanent(source.getFirstTarget());
        if (permanent == null || targeted == null) {
            return false;
        }
        int damage = permanent.getPower().getValue() + 2;
        Player player = game.getPlayer(permanent.getControllerId());
        if (damage > 0 && player != null && player.chooseUse(
                outcome, "Have " + permanent.getIdName() + " deal "
                        + damage + " to " + targeted.getIdName() + '?', source, game
        ) && targeted.damage(damage, permanent.getId(), source, game) > 0) {
            game.addEffect(new FarrelsMantleDamageEffect(mor), source);
            return true;
        }
        return false;
    }
}

class FarrelsMantleDamageEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    FarrelsMantleDamageEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.Neutral);
        this.mor = mor;
    }

    private FarrelsMantleDamageEffect(final FarrelsMantleDamageEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public FarrelsMantleDamageEffect copy() {
        return new FarrelsMantleDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((DamageEvent) event).isCombatDamage() && mor.refersTo(event.getSourceId(), game);
    }
}
