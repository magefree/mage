
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ThalakosDrifters extends CardImpl {

    public ThalakosDrifters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.THALAKOS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Discard a card: Thalakos Drifters gains shadow until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ShadowAbility.getInstance(), Duration.EndOfTurn), new DiscardCardCost()));
    }

    private ThalakosDrifters(final ThalakosDrifters card) {
        super(card);
    }

    @Override
    public ThalakosDrifters copy() {
        return new ThalakosDrifters(this);
    }
}
