
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class EldraziAggressor extends CardImpl {

    private static final String rule = "{this} has haste as long as you control another colorless creature";
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another colorless creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public EldraziAggressor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Eldrazi Aggressor has haste as long as you control another colorless creature.
        Effect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(HasteAbility.getInstance()), new PermanentsOnTheBattlefieldCondition(filter), rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private EldraziAggressor(final EldraziAggressor card) {
        super(card);
    }

    @Override
    public EldraziAggressor copy() {
        return new EldraziAggressor(this);
    }
}
