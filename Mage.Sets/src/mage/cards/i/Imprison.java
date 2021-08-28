
package mage.cards.i;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.BlockedByOnlyOneCreatureThisCombatWatcher;

/**
 *
 * @author L_J
 */
public final class Imprison extends CardImpl {

    public Imprison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);
        
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever a player activates an ability of enchanted creature with {T} in its activation cost that isn't a mana ability, you may pay {1}. If you do, counter that ability. If you don't, destroy Imprison.
        this.addAbility(new ImprisonTriggeredAbility());
        
        // Whenever enchanted creature attacks or blocks, you may pay {1}. If you do, tap the creature, remove it from combat, and creatures it was blocking that had become blocked by only that creature this combat become unblocked. If you don't, destroy Imprison.
        this.addAbility(new AttacksOrBlocksAttachedTriggeredAbility(new DoIfCostPaid(new ImprisonUnblockEffect(), new DestroySourceEffect(), new ManaCostsImpl("1")), AttachmentType.AURA));
    }

    private Imprison(final Imprison card) {
        super(card);
    }

    @Override
    public Imprison copy() {
        return new Imprison(this);
    }
}

class ImprisonTriggeredAbility extends TriggeredAbilityImpl {

    ImprisonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CounterTargetEffect().setText("counter that ability"), new DestroySourceEffect(), new ManaCostsImpl("1")));
    }

    ImprisonTriggeredAbility(final ImprisonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImprisonTriggeredAbility copy() {
        return new ImprisonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchantedPermanent = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (event.getSourceId().equals(enchantedPermanent.getId())) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    String abilityText = stackAbility.getRule(true);
                    if (abilityText.contains("{T},") || abilityText.contains("{T}:") || abilityText.contains("{T} or")) {
                        getEffects().get(0).setTargetPointer(new FixedTarget(stackAbility.getId()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a player activates an ability of enchanted creature with {T} in its activation cost that isn't a mana ability, " ;
    }
}

class ImprisonUnblockEffect extends OneShotEffect {

    public ImprisonUnblockEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap the creature, remove it from combat, and creatures it was blocking that had become blocked by only that creature this combat become unblocked";
    }

    public ImprisonUnblockEffect(final ImprisonUnblockEffect effect) {
        super(effect);
    }

    @Override
    public ImprisonUnblockEffect copy() {
        return new ImprisonUnblockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                if (permanent.isCreature(game)) {
                    
                    // Tap the creature
                    permanent.tap(source, game);
    
                    // Remove it from combat
                    Effect effect = new RemoveFromCombatTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
    
                    // Make blocked creatures unblocked
                    BlockedByOnlyOneCreatureThisCombatWatcher watcher = game.getState().getWatcher(BlockedByOnlyOneCreatureThisCombatWatcher.class);
                    if (watcher != null) {
                        Set<CombatGroup> combatGroups = watcher.getBlockedOnlyByCreature(permanent.getId());
                        if (combatGroups != null) {
                            for (CombatGroup combatGroup : combatGroups) {
                                if (combatGroup != null) {
                                    combatGroup.setBlocked(false, game);
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
