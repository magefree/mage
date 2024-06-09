package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class AcesBaseballBat extends CardImpl {

    private static final FilterControlledCreaturePermanent filterLegendary
            = new FilterControlledCreaturePermanent("legendary creature");
    static {
        filterLegendary.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final FilterControlledCreaturePermanent filterDalek
            = new FilterControlledCreaturePermanent("a Dalek");
    static {
        filterDalek.add(SubType.DALEK.getPredicate());
    }

    public AcesBaseballBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 0)));

        // As long as equipped creature is attacking, it has first strike and must be blocked by a Dalek if able.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                AttachedToAttackingCondition.instance, "As long as equipped creature is attacking, it has first strike"));
        ability.addEffect(new MustBeBlockedByAtLeastOneAttachedEffect(filterDalek).concatBy("and"));
        this.addAbility(ability);

        // Equip legendary creature (1)
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetControlledCreaturePermanent(filterLegendary), false));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    private AcesBaseballBat(final AcesBaseballBat card) {
        super(card);
    }

    @Override
    public AcesBaseballBat copy() {
        return new AcesBaseballBat(this);
    }
}
enum AttachedToAttackingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        return attachedTo.isAttacking();
    }
}
