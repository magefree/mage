
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class GoblinDiplomats extends CardImpl {

    public GoblinDiplomats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Each creature attacks this turn if able.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AttacksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_ALL_CREATURES, Duration.EndOfTurn)
                        .setText("Each creature attacks this turn if able"),
                new TapSourceCost()));
        
    }

    private GoblinDiplomats(final GoblinDiplomats card) {
        super(card);
    }

    @Override
    public GoblinDiplomats copy() {
        return new GoblinDiplomats(this);
    }
}
