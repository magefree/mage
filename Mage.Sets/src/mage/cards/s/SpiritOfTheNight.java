
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class SpiritOfTheNight extends CardImpl {
    
    public SpiritOfTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        
        // Spirit of the Night has first strike as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), SourceAttackingCondition.instance, "{this} has first strike as long as it's attacking")));
    }

    private SpiritOfTheNight(final SpiritOfTheNight card) {
        super(card);
    }

    @Override
    public SpiritOfTheNight copy() {
        return new SpiritOfTheNight(this);
    }
}
