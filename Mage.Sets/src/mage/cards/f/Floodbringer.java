
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class Floodbringer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land you control (return to hand)");

    public Floodbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());

        // {2}, Return a land you control to its owner's hand: Tap target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new GenericManaCost(2));
        ReturnToHandChosenControlledPermanentCost cost = new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter));
        cost.setText("Return a land you control to its owner's hand");
        ability.addCost(cost);
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private Floodbringer(final Floodbringer card) {
        super(card);
    }

    @Override
    public Floodbringer copy() {
        return new Floodbringer(this);
    }
}
