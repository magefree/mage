
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class WatchwingScarecrow extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a white creature");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("a blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final String rule = "{this} has vigilance as long as you control a white creature";
    private static final String rule2 = "{this} has flying as long as you control a blue creature";

    public WatchwingScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Watchwing Scarecrow has vigilance as long as you control a white creature.
        ContinuousEffect effect = new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new PermanentsOnTheBattlefieldCondition(filter), rule)));

        // Watchwing Scarecrow has flying as long as you control a blue creature.
        ContinuousEffect effect2 = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect2, new PermanentsOnTheBattlefieldCondition(filter2), rule2)));

    }

    private WatchwingScarecrow(final WatchwingScarecrow card) {
        super(card);
    }

    @Override
    public WatchwingScarecrow copy() {
        return new WatchwingScarecrow(this);
    }
}
