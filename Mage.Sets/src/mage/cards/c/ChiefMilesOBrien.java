package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ChiefMilesOBrien extends CardImpl {

    public ChiefMilesOBrien(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Seek out -- Whenever you draw your second card each turn, double the number of each kind of counter on target artifact or creature you control.
        Ability ability = new DrawNthCardTriggeredAbility(new DoubleCountersTargetEffect())
            .withFlavorWord("Seek out");
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private ChiefMilesOBrien(final ChiefMilesOBrien card) {
        super(card);
    }

    @Override
    public ChiefMilesOBrien copy() {
        return new ChiefMilesOBrien(this);
    }
}
