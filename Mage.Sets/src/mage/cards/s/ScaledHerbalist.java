package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class ScaledHerbalist extends CardImpl {

    public ScaledHerbalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: You may put a land card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A),
                new TapSourceCost()
        ));
    }

    private ScaledHerbalist(final ScaledHerbalist card) {
        super(card);
    }

    @Override
    public ScaledHerbalist copy() {
        return new ScaledHerbalist(this);
    }
}
