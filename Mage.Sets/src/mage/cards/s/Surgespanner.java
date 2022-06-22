
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Surgespanner extends CardImpl {

    public Surgespanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Surgespanner becomes tapped, you may pay {1}{U}. If you do, return target permanent to its owner's hand.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new DoIfCostPaid(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}")));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private Surgespanner(final Surgespanner card) {
        super(card);
    }

    @Override
    public Surgespanner copy() {
        return new Surgespanner(this);
    }
}
