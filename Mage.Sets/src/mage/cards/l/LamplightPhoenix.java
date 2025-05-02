package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LamplightPhoenix extends CardImpl {

    public LamplightPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Lamplight Phoenix dies, you may exile it and collect evidence 4. If you do, return Lamplight Phoenix to the battlefield tapped.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new ReturnToBattlefieldUnderOwnerControlSourceEffect(true)
                        .setText("return {this} to the battlefield tapped"),
                new CompositeCost(new ExileSourceCost(), new CollectEvidenceCost(4),
                        "exile it and collect evidence 4"))
        ));
    }

    private LamplightPhoenix(final LamplightPhoenix card) {
        super(card);
    }

    @Override
    public LamplightPhoenix copy() {
        return new LamplightPhoenix(this);
    }
}
