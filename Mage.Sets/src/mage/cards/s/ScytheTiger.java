
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Rafbill
 */
public final class ScytheTiger extends CardImpl {

    public ScytheTiger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Shroud (This creature can't be the target of spells or abilities.)
        this.addAbility(ShroudAbility.getInstance());

        // When Scythe Tiger enters the battlefield, sacrifice it unless you sacrifice a land.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT))).setText("sacrifice it unless you sacrifice a land")));
    }

    private ScytheTiger(final ScytheTiger card) {
        super(card);
    }

    @Override
    public ScytheTiger copy() {
        return new ScytheTiger(this);
    }
}
