package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeothermalKami extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledEnchantmentPermanent("an enchantment you control");

    public GeothermalKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Geothermal Kami enters the battlfield, you may return an enchantment you control to its owner's hand. If you do, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new GainLifeEffect(3), new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))
        )));
    }

    private GeothermalKami(final GeothermalKami card) {
        super(card);
    }

    @Override
    public GeothermalKami copy() {
        return new GeothermalKami(this);
    }
}
