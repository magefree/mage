package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public class BullElephant extends CardImpl {

    private static FilterControlledLandPermanent controlledForest = new FilterControlledLandPermanent("two forests you control");

    static {
        controlledForest.add(new SubtypePredicate(SubType.FOREST));
    }


    public BullElephant(UUID cardId, CardSetInfo cardSetInfo) {
        super(cardId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add("Elephant");
        power = new MageInt(4);
        toughness = new MageInt(4);
//When Bull Elephant enters the battlefield, sacrifice it unless you return two Forests you control to their owner's hand.
        addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(2, 2, controlledForest, false)))));
    }

    public BullElephant(BullElephant other) {
        super(other);
    }

    public BullElephant copy() {
        return new BullElephant(this);
    }
}
