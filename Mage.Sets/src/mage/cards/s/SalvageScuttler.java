package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SalvageScuttler extends CardImpl {

    public SalvageScuttler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Salvage Scuttler attacks, return an artifact you control to its owner's hand.
        this.addAbility(new AttacksTriggeredAbility(
                new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT)
        ));
    }

    private SalvageScuttler(final SalvageScuttler card) {
        super(card);
    }

    @Override
    public SalvageScuttler copy() {
        return new SalvageScuttler(this);
    }
}
