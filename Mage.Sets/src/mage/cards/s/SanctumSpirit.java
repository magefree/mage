
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterHistoricCard;

/**
 *
 * @author keelahnkhan
 */
public final class SanctumSpirit extends CardImpl {

    public SanctumSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Discard a historic card: Sanctum Spirit gains indestructible until end of turn.
        this.addAbility(
            new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), 
                new DiscardCardCost(new FilterHistoricCard())
            )
        );
    }

    private SanctumSpirit(final SanctumSpirit card) {
        super(card);
    }

    @Override
    public SanctumSpirit copy() {
        return new SanctumSpirit(this);
    }
}
