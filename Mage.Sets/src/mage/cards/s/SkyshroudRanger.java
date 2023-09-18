
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class SkyshroudRanger extends CardImpl {

    public SkyshroudRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: You may put a land card from your hand onto the battlefield. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A), 
                new TapSourceCost()
        ));

    }

    private SkyshroudRanger(final SkyshroudRanger card) {
        super(card);
    }

    @Override
    public SkyshroudRanger copy() {
        return new SkyshroudRanger(this);
    }
}
