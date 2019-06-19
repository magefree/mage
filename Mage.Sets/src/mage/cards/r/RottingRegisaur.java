package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RottingRegisaur extends CardImpl {

    public RottingRegisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, discard a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DiscardControllerEffect(1),
                TargetController.YOU, false
        ));
    }

    private RottingRegisaur(final RottingRegisaur card) {
        super(card);
    }

    @Override
    public RottingRegisaur copy() {
        return new RottingRegisaur(this);
    }
}
