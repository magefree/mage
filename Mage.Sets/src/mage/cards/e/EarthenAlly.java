package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthenAlly extends CardImpl {

    public EarthenAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // This creature gets +1/+0 for each color among Allies you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                ColorsAmongControlledPermanentsCount.ALLIES, StaticValue.get(0), Duration.WhileOnBattlefield
        )).addHint(ColorsAmongControlledPermanentsCount.ALLIES.getHint()));

        // {2}{W}{U}{B}{R}{G}: Earthbend 5.
        Ability ability = new SimpleActivatedAbility(
                new EarthbendTargetEffect(5), new ManaCostsImpl<>("{2}{W}{U}{B}{R}{G}")
        );
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private EarthenAlly(final EarthenAlly card) {
        super(card);
    }

    @Override
    public EarthenAlly copy() {
        return new EarthenAlly(this);
    }
}
