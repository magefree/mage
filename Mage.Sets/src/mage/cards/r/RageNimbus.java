
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class RageNimbus extends CardImpl {

    public RageNimbus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AttacksIfAbleTargetEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RageNimbus(final RageNimbus card) {
        super(card);
    }

    @Override
    public RageNimbus copy() {
        return new RageNimbus(this);
    }
}
