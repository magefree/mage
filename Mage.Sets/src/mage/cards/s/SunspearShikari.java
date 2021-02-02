
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class SunspearShikari extends CardImpl {

    public SunspearShikari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as Sunspear Shikari is equipped, it has first strike and lifelink.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()), 
                EquippedSourceCondition.instance, "As long as {this} is equipped, it has first strike");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ConditionalContinuousEffect effect2 = new ConditionalContinuousEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance()), 
                EquippedSourceCondition.instance, "and lifelink");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private SunspearShikari(final SunspearShikari card) {
        super(card);
    }

    @Override
    public SunspearShikari copy() {
        return new SunspearShikari(this);
    }
}
