package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ThousandMoonsCrackshot extends CardImpl {

    public ThousandMoonsCrackshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Thousand Moons Crackshot attacks, you may pay {2}{W}. When you do, tap target creature.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        reflexive.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                reflexive, new ManaCostsImpl<>("{2}{W}"), "Pay {2}{W} to tap target creature?"
        )));

    }

    private ThousandMoonsCrackshot(final ThousandMoonsCrackshot card) {
        super(card);
    }

    @Override
    public ThousandMoonsCrackshot copy() {
        return new ThousandMoonsCrackshot(this);
    }
}
