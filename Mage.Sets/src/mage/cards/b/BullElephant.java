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
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

public final class BullElephant extends CardImpl {

    private static final FilterControlledLandPermanent controlledForest = new FilterControlledLandPermanent("Forests");

    static {
        controlledForest.add(SubType.FOREST.getPredicate());
    }


    public BullElephant(UUID cardId, CardSetInfo cardSetInfo) {
        super(cardId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELEPHANT);

        power = new MageInt(4);
        toughness = new MageInt(4);

        // When Bull Elephant enters the battlefield, sacrifice it unless you return two Forests you control to their owner's hand.
        addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandChosenControlledPermanentCost(
                new TargetControlledPermanent(2, 2, controlledForest, false)
        ))));
    }

    public BullElephant(BullElephant other) {
        super(other);
    }

    public BullElephant copy() {
        return new BullElephant(this);
    }
}
