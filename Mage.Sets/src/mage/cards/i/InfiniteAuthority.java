package mage.cards.i;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class InfiniteAuthority extends CardImpl {

    public InfiniteAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature blocks or becomes blocked by a creature with toughness 3 or less, destroy the other creature at end of combat. At the beginning of the next end step, if that creature was destroyed this way, put a +1/+1 counter on the first creature.
        this.addAbility(new InfiniteAuthorityTriggeredAbility());
    }

    private InfiniteAuthority(final InfiniteAuthority card) {
        super(card);
    }

    @Override
    public InfiniteAuthority copy() {
        return new InfiniteAuthority(this);
    }
}

class InfiniteAuthorityTriggeredAbility extends TriggeredAbilityImpl {

    InfiniteAuthorityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new InfiniteAuthorityEffect())));
    }

    InfiniteAuthorityTriggeredAbility(final InfiniteAuthorityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public InfiniteAuthorityTriggeredAbility copy() {
        return new InfiniteAuthorityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = game.getPermanentOrLKIBattlefield(sourceId);
        if (aura != null) {
            Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(aura.getAttachedTo());
            if (enchantedCreature != null) {
                Permanent blocker = game.getPermanent(event.getSourceId());
                Permanent blocked = game.getPermanent(event.getTargetId());
                Effect effect = this.getEffects().get(0);
                if (blocker != null
                        && Objects.equals(blocked, enchantedCreature)
                        && blocker.getToughness().getValue() <= 3) {
                    effect.setTargetPointer(new FixedTarget(blocker.getId(), game));
                    return true;
                }
                if (blocked != null
                        && Objects.equals(blocker, enchantedCreature)
                        && blocked.getToughness().getValue() <= 3) {
                    effect.setTargetPointer(new FixedTarget(blocked.getId(), game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever enchanted creature blocks or becomes blocked by a creature with toughness 3 or less, ";
    }
}

class InfiniteAuthorityEffect extends OneShotEffect {

    InfiniteAuthorityEffect() {
        super(Outcome.Detriment);
        staticText = "destroy the other creature at end of combat. At the beginning of the next end step, if that creature was destroyed this way, put a +1/+1 counter on the first creature";
    }

    InfiniteAuthorityEffect(final InfiniteAuthorityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (aura != null) {
            Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(aura.getAttachedTo());
            if (enchantedCreature != null) {
                Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    if (permanent.destroy(source, game, false)) {
                        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
                        delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(enchantedCreature, game));
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public InfiniteAuthorityEffect copy() {
        return new InfiniteAuthorityEffect(this);
    }
}
