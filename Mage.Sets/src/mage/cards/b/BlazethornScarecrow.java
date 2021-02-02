
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class BlazethornScarecrow extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a red creature");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("a green creature");
    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ColorPredicate(ObjectColor.GREEN));
    }
    
    private static final String rule = "{this} has haste as long as you control a red creature";
    private static final String rule2 = "{this} has wither as long as you control a green creature";
    
    public BlazethornScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Blazethorn Scarecrow has haste as long as you control a red creature.
        ContinuousEffect effect = new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new PermanentsOnTheBattlefieldCondition(filter), rule)));
        
        // Blazethorn Scarecrow has wither as long as you control a green creature.
        ContinuousEffect effect2 = new GainAbilitySourceEffect(WitherAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect2, new PermanentsOnTheBattlefieldCondition(filter2), rule2)));
        
    }

    private BlazethornScarecrow(final BlazethornScarecrow card) {
        super(card);
    }

    @Override
    public BlazethornScarecrow copy() {
        return new BlazethornScarecrow(this);
    }
}
