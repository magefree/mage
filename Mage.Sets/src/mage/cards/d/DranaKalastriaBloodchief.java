
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DranaKalastriaBloodchief extends CardImpl {

    public DranaKalastriaBloodchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
       addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(StaticValue.get(0), new SignInversionDynamicValue(ManacostVariableValue.REGULAR), Duration.EndOfTurn), new ManaCostsImpl<>("{X}{B}{B}"));
        ability.addEffect(new BoostSourceEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn).concatBy("and"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DranaKalastriaBloodchief(final DranaKalastriaBloodchief card) {
        super(card);
    }

    @Override
    public DranaKalastriaBloodchief copy() {
        return new DranaKalastriaBloodchief(this);
    }
}
