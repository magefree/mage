package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MobMentality extends CardImpl {

    public MobMentality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA)));

        // Whenever all non-Wall creatures you control attack, enchanted creature gets +X/+0 until end of turn, where X is the number of attacking creatures.
        this.addAbility(new MobMentalityTriggeredAbility());
    }

    private MobMentality(final MobMentality card) {
        super(card);
    }

    @Override
    public MobMentality copy() {
        return new MobMentality(this);
    }
}

class MobMentalityTriggeredAbility extends TriggeredAbilityImpl {
    MobMentalityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(new AttackingCreatureCount(), StaticValue.get(0), Duration.EndOfTurn));
    }

    private MobMentalityTriggeredAbility(final MobMentalityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MobMentalityTriggeredAbility copy() {
        return new MobMentalityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackingPlayerId().equals(getControllerId())) {
            return false;
        }
        Permanent aura = game.getPermanent(getSourceId());
        if (aura == null) {
            return false;
        }
        Permanent creature = game.getPermanent(aura.getAttachedTo());
        if (creature == null) {
            return false;
        }
        for (Effect effect : getEffects()) {
            effect.setTargetPointer(new FixedTarget(creature, game));
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(getControllerId())) {
            if (permanent.isCreature(game)
                    && !permanent.hasSubtype(SubType.WALL, game)
                    && !permanent.isAttacking()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever all non-Wall creatures you control attack, " +
                "enchanted creature gets +X/+0 until end of turn, " +
                "where X is the number of attacking creatures.";
    }
}
