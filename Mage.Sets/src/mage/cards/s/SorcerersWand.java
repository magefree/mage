
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class SorcerersWand extends CardImpl {

    public SorcerersWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{T}: This creature deals 1 damage to target player or planeswalker. If this creature is a Wizard, it deals 2 damage to that player or planeswalker instead."
        Ability ability = new SimpleActivatedAbility(
                new ConditionalOneShotEffect(
                        new DamageTargetEffect(2),
                        new DamageTargetEffect(1),
                        new SourceHasSubtypeCondition(SubType.WIZARD),
                        "This creature deals 1 damage to target player or planeswalker. "
                        + "If this creature is a Wizard, it deals 2 damage to that player or planeswalker instead."
                ), new TapSourceCost()
        );
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private SorcerersWand(final SorcerersWand card) {
        super(card);
    }

    @Override
    public SorcerersWand copy() {
        return new SorcerersWand(this);
    }
}
