
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class CephalidInkshrouder extends CardImpl {

    public CephalidInkshrouder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.CEPHALID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Discard a card: Cephalid Inkshrouder gains shroud until end of turn and is unblockable this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ShroudAbility.getInstance(),Duration.EndOfTurn), new DiscardCardCost());
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public CephalidInkshrouder(final CephalidInkshrouder card) {
        super(card);
    }

    @Override
    public CephalidInkshrouder copy() {
        return new CephalidInkshrouder(this);
    }
}
