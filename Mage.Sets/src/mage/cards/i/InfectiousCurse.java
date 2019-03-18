
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author halljared
 */
public final class InfectiousCurse extends CardImpl {

    public InfectiousCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"");
        this.subtype.add(SubType.AURA, SubType.CURSE);
        this.color.setBlack(true);

        this.nightCard = true;
        this.transformable = true;

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Spells you cast that target enchanted player cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfectiousCurseCostReductionEffect()));
        // At the beginning of enchanted player's upkeep, that player loses 1 life and you gain 1 life.
        InfectiousCurseAbility curseAbility = new InfectiousCurseAbility();
        curseAbility.addEffect(new GainLifeEffect(1));
        this.addAbility(curseAbility);
    }

    public InfectiousCurse(final InfectiousCurse card) {
        super(card);
    }

    @Override
    public InfectiousCurse copy() {
        return new InfectiousCurse(this);
    }
}

class InfectiousCurseAbility extends TriggeredAbilityImpl {

    public InfectiousCurseAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1));
    }

    public InfectiousCurseAbility(final InfectiousCurseAbility ability) {
        super(ability);
    }

    @Override
    public InfectiousCurseAbility copy() {
        return new InfectiousCurseAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null && game.isActivePlayer(player.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, that player loses 1 life and you gain 1 life.";
    }

}

class InfectiousCurseCostReductionEffect extends CostModificationEffectImpl {

    public InfectiousCurseCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells you cast that target enchanted player cost {1} less to cast.";
    }

    protected InfectiousCurseCostReductionEffect(InfectiousCurseCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (source.isControlledBy(abilityToModify.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            Permanent enchantment = game.getPermanent(source.getSourceId());
                            UUID attachedTo = enchantment.getAttachedTo();
                            if (targetUUID.equals(attachedTo)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public InfectiousCurseCostReductionEffect copy() {
        return new InfectiousCurseCostReductionEffect(this);
    }
}
