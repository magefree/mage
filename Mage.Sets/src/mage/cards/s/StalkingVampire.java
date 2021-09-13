package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class StalkingVampire extends CardImpl {

    public StalkingVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.VAMPIRE);
        this.color.setBlack(true);

        this.nightCard = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Stalking Vampire.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect(),
                new ManaCostsImpl<>("{2}{B}{B}")
        ), TargetController.YOU, false));
    }

    private StalkingVampire(final StalkingVampire card) {
        super(card);
    }

    @Override
    public StalkingVampire copy() {
        return new StalkingVampire(this);
    }
}
