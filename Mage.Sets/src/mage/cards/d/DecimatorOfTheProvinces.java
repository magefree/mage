
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class DecimatorOfTheProvinces extends CardImpl {

    public DecimatorOfTheProvinces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{10}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Emerge {6}{G}{G}{G}
        this.addAbility(new EmergeAbility(this, "{6}{G}{G}{G}"));

        // When you cast Decimator of the Provinces, creatures you control get +2/+2 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(2, 2, Duration.EndOfTurn);
        effect.setText("creatures you control get +2/+2");
        Ability ability = new CastSourceTriggeredAbility(effect);
        effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private DecimatorOfTheProvinces(final DecimatorOfTheProvinces card) {
        super(card);
    }

    @Override
    public DecimatorOfTheProvinces copy() {
        return new DecimatorOfTheProvinces(this);
    }
}
