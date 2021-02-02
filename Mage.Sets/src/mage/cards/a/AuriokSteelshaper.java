
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Jason E. Wall
 *
 */
public final class AuriokSteelshaper extends CardImpl {

    private static final FilterCreaturePermanent soldiersOrKnights = new FilterCreaturePermanent();

    static {
        soldiersOrKnights.add(Predicates.or(
                SubType.SOLDIER.getPredicate(),
                SubType.KNIGHT.getPredicate()
        ));
    }

    public AuriokSteelshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Equip costs you pay cost {1} less.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbilitiesCostReductionControllerEffect(EquipAbility.class, "Equip")));

        // As long as Auriok Steelshaper is equipped, each creature you control that's a Soldier or a Knight gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, soldiersOrKnights, false),
                EquippedSourceCondition.instance,
                "As long as {this} is equipped, each creature you control that's a Soldier or a Knight gets +1/+1"
        )));
    }

    private AuriokSteelshaper(final AuriokSteelshaper card) {
        super(card);
    }

    @Override
    public AuriokSteelshaper copy() {
        return new AuriokSteelshaper(this);
    }
}
