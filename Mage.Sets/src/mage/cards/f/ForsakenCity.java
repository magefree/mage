package mage.cards.f;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Luna Skyrise
 */
public final class ForsakenCity extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card from your hand");

    public ForsakenCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Forsaken City doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // At the beginning of your upkeep, you may exile a card from your hand. If you do, untap Forsaken City.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new UntapSourceEffect(), new ExileFromHandCost(new TargetCardInHand(filter))), TargetController.YOU, false));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ForsakenCity(final ForsakenCity card) {
        super(card);
    }

    @Override
    public ForsakenCity copy() {
        return new ForsakenCity(this);
    }
}
