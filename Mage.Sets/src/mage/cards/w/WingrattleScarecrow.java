
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
public final class WingrattleScarecrow extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a blue creature");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("a black creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter2.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    private static final String rule = "{this} has flying as long as you control a blue creature";
    private static final String rule2 = "{this} has persist as long as you control a black creature";

    public WingrattleScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Wingrattle Scarecrow has flying as long as you control a blue creature.
        ContinuousEffect effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new PermanentsOnTheBattlefieldCondition(filter), rule)));
        
        // Wingrattle Scarecrow has persist as long as you control a black creature.
        ContinuousEffect effect2 = new GainAbilitySourceEffect(new PersistAbility(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect2, new PermanentsOnTheBattlefieldCondition(filter2), rule2)));
        
    }

    private WingrattleScarecrow(final WingrattleScarecrow card) {
        super(card);
    }

    @Override
    public WingrattleScarecrow copy() {
        return new WingrattleScarecrow(this);
    }
}
