
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ThunderfootBaloth extends CardImpl {

    public ThunderfootBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Lieutenant - As long as you control your commander, Thunderfoot Baloth gets +2/+2 and other creatures you control get +2/+2 and have trample.
        Effects effects = new Effects();
        Effect effect = new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, true);
        effect.setText("and other creatures you control get +2/+2");
        effects.add(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent(), true);
        effect.setText("and have trample");
        effects.add(effect);
        this.addAbility(new LieutenantAbility(effects));
    }

    private ThunderfootBaloth(final ThunderfootBaloth card) {
        super(card);
    }

    @Override
    public ThunderfootBaloth copy() {
        return new ThunderfootBaloth(this);
    }
}
