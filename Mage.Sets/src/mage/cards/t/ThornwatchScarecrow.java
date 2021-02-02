
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WitherAbility;
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
public final class ThornwatchScarecrow extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a green creature");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("a white creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
    }

    private static final String rule = "{this} has wither as long as you control a green creature";
    private static final String rule2 = "{this} has vigilance as long as you control a white creature";

    public ThornwatchScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Thornwatch Scarecrow has wither as long as you control a green creature.
        ContinuousEffect effect = new GainAbilitySourceEffect(WitherAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new PermanentsOnTheBattlefieldCondition(filter), rule)));

        // Thornwatch Scarecrow has vigilance as long as you control a white creature.
        ContinuousEffect effect2 = new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect2, new PermanentsOnTheBattlefieldCondition(filter2), rule2)));
        
    }

    private ThornwatchScarecrow(final ThornwatchScarecrow card) {
        super(card);
    }

    @Override
    public ThornwatchScarecrow copy() {
        return new ThornwatchScarecrow(this);
    }
}
