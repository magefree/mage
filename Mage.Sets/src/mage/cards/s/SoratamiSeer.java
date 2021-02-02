
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class SoratamiSeer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("lands");

    public SoratamiSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {4}, Return two lands you control to their owner's hand: Discard all the cards in your hand, then draw that many cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardHandDrawSameNumberSourceEffect(), new GenericManaCost(4));
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, filter, false)));
        this.addAbility(ability);
    }

    private SoratamiSeer(final SoratamiSeer card) {
        super(card);
    }

    @Override
    public SoratamiSeer copy() {
        return new SoratamiSeer(this);
    }

}
