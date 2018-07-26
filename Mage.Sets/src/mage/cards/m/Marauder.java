package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Marauder extends CardImpl {

    public Marauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Marauder attacks, you may pay {1}{W}. If you do, tap target creature.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new TapTargetEffect(), new ManaCostsImpl("{1}{W}")), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public Marauder(final Marauder card) {
        super(card);
    }

    @Override
    public Marauder copy() {
        return new Marauder(this);
    }
}
