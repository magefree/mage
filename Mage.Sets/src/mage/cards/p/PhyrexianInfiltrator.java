
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PhyrexianInfiltrator extends CardImpl {

    public PhyrexianInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}{U}: Exchange control of Phyrexian Infiltrator and target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExchangeControlTargetEffect(Duration.EndOfGame,
            "Exchange control of {this} and target creature", true), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private PhyrexianInfiltrator(final PhyrexianInfiltrator card) {
        super(card);
    }

    @Override
    public PhyrexianInfiltrator copy() {
        return new PhyrexianInfiltrator(this);
    }
}
