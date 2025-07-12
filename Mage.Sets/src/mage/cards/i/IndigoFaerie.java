
package mage.cards.i;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class IndigoFaerie extends CardImpl {

    public IndigoFaerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {U}: Target permanent becomes blue in addition to its other colors until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BecomesColorTargetEffect(ObjectColor.BLUE, true, Duration.EndOfTurn),
                new ManaCostsImpl<>("{U}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        
    }

    private IndigoFaerie(final IndigoFaerie card) {
        super(card);
    }

    @Override
    public IndigoFaerie copy() {
        return new IndigoFaerie(this);
    }
}
