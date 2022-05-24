
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SpiritEnDal extends CardImpl {

    public SpiritEnDal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        
        // Forecast - {1}{W}, Reveal Spirit en-Dal from your hand: Target creature gains shadow until end of turn.
        Ability ability = new ForecastAbility(new GainAbilityTargetEffect(ShadowAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpiritEnDal(final SpiritEnDal card) {
        super(card);
    }

    @Override
    public SpiritEnDal copy() {
        return new SpiritEnDal(this);
    }
}
