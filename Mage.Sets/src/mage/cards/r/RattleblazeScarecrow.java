
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class RattleblazeScarecrow extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a black creature");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("a red creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter2.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final String rule = "{this} has persist as long as you control a black creature";
    private static final String rule2 = "{this} has haste as long as you control a red creature";

    public RattleblazeScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Rattleblaze Scarecrow has persist as long as you control a black creature.
        ContinuousEffect effect = new GainAbilitySourceEffect(new PersistAbility(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new PermanentsOnTheBattlefieldCondition(filter), rule)));

        // Rattleblaze Scarecrow has haste as long as you control a red creature.
        ContinuousEffect effect2 = new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect2, new PermanentsOnTheBattlefieldCondition(filter2), rule2)));

    }

    private RattleblazeScarecrow(final RattleblazeScarecrow card) {
        super(card);
    }

    @Override
    public RattleblazeScarecrow copy() {
        return new RattleblazeScarecrow(this);
    }
}
