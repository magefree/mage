
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class AngelicFieldMarshal extends CardImpl {

    public AngelicFieldMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Lieutenant - As long as you control your commander, Angelic Field Marshal gets +2/+2 and creatures you control have vigilance.
        ContinuousEffect effect = new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent());
        effect.setText("and creatures you control have vigilance");
        this.addAbility(new LieutenantAbility(effect));
    }

    private AngelicFieldMarshal(final AngelicFieldMarshal card) {
        super(card);
    }

    @Override
    public AngelicFieldMarshal copy() {
        return new AngelicFieldMarshal(this);
    }
}
