package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetControlledPermanent;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801, muz
 *
 * 702.190. Sneak
 *
 * 702.190a Sneak is a keyword that represents a static ability that functions
 * while the spell with sneak is on the stack. “Sneak [cost]” means “Any time
 * you could cast an instant during your declare blockers step, you may cast
 * this spell by paying [cost] and returning an unblocked creature you control
 * to its owner’s hand rather than paying this spell’s mana cost.”
 *
 * 702.190b A permanent spell whose sneak cost was paid enters the battlefield
 * tapped and attacking (see rule 506.3a). It will be attacking the same
 * player, planeswalker, or battle as the creature that was returned to its
 * owner’s hand to pay the sneak cost of the spell that became that permanent.
 */
public class SneakAbility extends SpellAbility {

    public static final String SNEAK_ACTIVATION_VALUE_KEY = "sneakActivation";
    public SneakAbility(Card card, String manaString) {
        super(card.getSpellAbility());
        this.newId();
        this.setCardName(card.getName() + " with Sneak");
        timing = TimingRule.INSTANT;
        zone = Zone.HAND;
        spellAbilityType = SpellAbilityType.BASE_ALTERNATE;

        this.clearManaCosts();
        this.clearManaCostsToPay();
        this.addCost(new ManaCostsImpl<>(manaString));
        this.addCost(new SneakReturnAttackerToHandCost());

        this.setRuleAtTheTop(true);

        // Sync targets and effects from the card's spell ability at target-selection
        // time. This allows SneakAbility to be constructed before addEffect/addTarget
        // are called on the card's spell ability (construction order doesn't matter).
        this.setTargetAdjuster((ability, game) -> {
            Card sourceCard = game.getCard(ability.getSourceId());
            if (sourceCard == null) {
                return;
            }
            Mode sneakMode = ability.getModes().getMode();
            Mode cardMode = sourceCard.getSpellAbility().getModes().getMode();
            sneakMode.getTargets().clear();
            cardMode.getTargets().forEach(t -> sneakMode.addTarget(t.copy()));
            sneakMode.getEffects().clear();
            cardMode.getEffects().forEach(e -> sneakMode.addEffect(e.copy()));
        });
    }

    protected SneakAbility(final SneakAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (game.getStep() == null
                || game.getStep().getType() != PhaseStep.DECLARE_BLOCKERS) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public void adjustTargets(Game game) {
        // Skip the second run (inside AbilityImpl.activate's mode loop) if
        // test-mode addTargets has already stored selections on the targets.
        // For non-test mode the second run is allowed to proceed normally,
        // since no selections exist yet and the targets need to be rebuilt.
        boolean hasSelections = getModes().getMode().getTargets().stream()
                .anyMatch(t -> !t.getTargets().isEmpty());
        if (!hasSelections) {
            super.adjustTargets(game);
        }
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        // Pre-run target adjustment before super.activate() so that the test-mode
        // addTargets call (which fires before adjustTargets inside AbilityImpl.activate)
        // can find the synced targets on the ability. The adjustTargets override above
        // prevents the second run (inside super) from overwriting those selections.
        adjustTargets(game);
        return super.activate(game, allowedIdentifiers, noMana);
    }

    @Override
    public SneakAbility copy() {
        return new SneakAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Sneak ");
        sb.append(getManaCosts().getText());
        sb.append(" <i>(You may cast this spell for ");
        sb.append(getManaCosts().getText());
        sb.append(" if you also return an unblocked attacker you control to hand during the declare blockers step.)</i>");
        return sb.toString();
    }
}

class SneakEntersTappedAndAttackingEffect extends ReplacementEffectImpl {

    private final MageObjectReference sneakSpellMOR;
    private final UUID defenderId;

    SneakEntersTappedAndAttackingEffect(MageObjectReference sneakSpellMOR, UUID defenderId) {
        super(Duration.OneUse, Outcome.Benefit);
        this.sneakSpellMOR = sneakSpellMOR;
        this.defenderId = defenderId;
    }

    private SneakEntersTappedAndAttackingEffect(final SneakEntersTappedAndAttackingEffect effect) {
        super(effect);
        this.sneakSpellMOR = effect.sneakSpellMOR;
        this.defenderId = effect.defenderId;
    }

    @Override
    public SneakEntersTappedAndAttackingEffect copy() {
        return new SneakEntersTappedAndAttackingEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell morSpell = sneakSpellMOR.getSpell(game);
        if (morSpell == null) {
            discard();
            return false;
        }
        // The permanent entering must be the sneaked card itself (same card ID).
        // This prevents the effect from firing when a non-creature sneak spell
        // (e.g. a sorcery) puts OTHER permanents onto the battlefield via its effect.
        if (!morSpell.getSourceId().equals(event.getTargetId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getSourceId());
        return spell != null
                && morSpell.getSourceId().equals(spell.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent == null || game.getCombat() == null) {
            return false;
        }
        // Enter tapped
        permanent.setTapped(true);
        // Enter attacking — build a CombatGroup directly, since addAttackerToCombat
        // needs game.getPermanent() which returns null while the permanent is still entering
        Permanent defender = game.getPermanent(defenderId);
        UUID defendingPlayerId;
        if (defender == null) {
            if (game.getPlayer(defenderId) == null) {
                // The original attack target (planeswalker/battle) no longer exists;
                // enter tapped but skip the attacking assignment
                return false;
            }
            defendingPlayerId = defenderId; // defender is a player
        } else if (defender.isPlaneswalker(game)) {
            defendingPlayerId = defender.getControllerId();
        } else if (defender.isBattle(game)) {
            defendingPlayerId = defender.getProtectorId();
        } else {
            return false; // not a valid defender
        }
        CombatGroup combatGroup = new CombatGroup(defenderId, defender != null, defendingPlayerId);
        combatGroup.getAttackers().add(permanent.getId());
        permanent.setAttacking(new MageObjectReference(defenderId, game));
        game.getCombat().getGroups().add(combatGroup);
        return false; // modify but don't replace the ETB event
    }
}

class SneakReturnAttackerToHandCost extends ReturnToHandChosenControlledPermanentCost {

    private static final FilterControlledCreaturePermanent sneakFilter = new FilterControlledCreaturePermanent("unblocked attacker you control");

    static {
        sneakFilter.add(UnblockedPredicate.instance);
    }

    private UUID defenderId;

    SneakReturnAttackerToHandCost() {
        super(new TargetControlledPermanent(sneakFilter));
        this.setText("");
    }

    private SneakReturnAttackerToHandCost(final SneakReturnAttackerToHandCost cost) {
        super(cost);
        this.defenderId = cost.defenderId;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        // Register the ETB effect now that defenderId is known (set in addReturnTarget during super.pay())
        ability.setCostsTag(SneakAbility.SNEAK_ACTIVATION_VALUE_KEY, null);
        if (defenderId != null) {
            MageObjectReference sneakSpellMOR = new MageObjectReference(ability.getSourceId(), game);
            game.getState().addEffect(
                    new SneakEntersTappedAndAttackingEffect(sneakSpellMOR, defenderId),
                    ability
            );
        }
        return true;
    }

    @Override
    protected void addReturnTarget(Game game, Permanent permanent) {
        super.addReturnTarget(game, permanent);
        defenderId = game.getCombat().getDefenderId(permanent.getId());
    }

    @Override
    public SneakReturnAttackerToHandCost copy() {
        return new SneakReturnAttackerToHandCost(this);
    }
}
