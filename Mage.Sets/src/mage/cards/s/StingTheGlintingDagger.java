package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class StingTheGlintingDagger extends CardImpl {

    public StingTheGlintingDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has haste.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
            HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has haste"));
        this.addAbility(ability);

        // At the beginning of each combat, untap equipped creature.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
            new UntapAttachedEffect(AttachmentType.EQUIPMENT, "creature"),
            TargetController.ANY, false));

        // Equipped creature has first strike as long as it's blocking or blocked by a Goblin or Orc.
        this.addAbility(new SimpleStaticAbility(
            new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                StingTheGlintingDaggerCondition.instance,
                "Equipped creature has first strike as long as it's blocking or blocked by a Goblin or Orc."
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private StingTheGlintingDagger(final StingTheGlintingDagger card) {
        super(card);
    }

    @Override
    public StingTheGlintingDagger copy() {
        return new StingTheGlintingDagger(this);
    }
}

enum StingTheGlintingDaggerCondition implements Condition {
    instance;

    public static final FilterPermanent filterOrcOrGoblin = new FilterCreaturePermanent();

    static {
        filterOrcOrGoblin.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate()
        ));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sting = game.getPermanent(source.getSourceId());
        if (sting == null) {
            return false;
        }

        Permanent equippedCreature = game.getPermanent(sting.getAttachedTo());
        if (equippedCreature == null) {
            return false;
        }

        if (equippedCreature.isAttacking() && equippedCreature.isBlocked(game)) {
            // equipped creature is currently blocked, time to look for blocking goblin or orc.
            UUID controllerId = equippedCreature.getControllerId();

            return game.getBattlefield()
                .getActivePermanents(filterOrcOrGoblin, controllerId, game)
                .stream()
                .anyMatch(p -> BlockingOrBlockedWatcher.check(equippedCreature, p, game));

        } else if (equippedCreature.getBlocking() > 0) {
            // equipped creature is currently blocking, time to look for blocked goblin or orc.
            UUID controllerId = equippedCreature.getControllerId();

            return game.getBattlefield()
                .getActivePermanents(filterOrcOrGoblin, controllerId, game)
                .stream()
                .anyMatch(p -> BlockingOrBlockedWatcher.check(p, equippedCreature, game));
        }
        // creature is neither blocking nor blocked, no first strike from Sting.
        return false;
    }
}
