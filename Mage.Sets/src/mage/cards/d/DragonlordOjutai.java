
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author fireshoes
 */
public final class DragonlordOjutai extends CardImpl {

    public DragonlordOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Dragonlord Ojutai has hexproof as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED,
                "{this} has hexproof as long as it's untapped")));
        
        // Whenever Dragonlord Ojutai deals combat damage to a player, look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LookLibraryAndPickControllerEffect
            (StaticValue.get(3), false, StaticValue.get(1), new FilterCard(), Zone.LIBRARY, false, false), false));
    }

    private DragonlordOjutai(final DragonlordOjutai card) {
        super(card);
    }

    @Override
    public DragonlordOjutai copy() {
        return new DragonlordOjutai(this);
    }
}
