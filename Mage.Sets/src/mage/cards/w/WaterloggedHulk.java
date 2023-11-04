package mage.cards.w;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WaterloggedHulk extends CardImpl {

    public WaterloggedHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");
        this.secondSideCardClazz = mage.cards.w.WatertightGondola.class;

        // {T}: Mill a card.
        this.addAbility(new SimpleActivatedAbility(
                new MillCardsControllerEffect(1),
                new TapSourceCost()
        ));

        // Craft with Island {3}{U}
        this.addAbility(new CraftAbility(
                "{3}{U}", "Island",
                "another Island you control or an Island card from your graveyard",
                SubType.ISLAND.getPredicate()
        ));
    }

    private WaterloggedHulk(final WaterloggedHulk card) {
        super(card);
    }

    @Override
    public WaterloggedHulk copy() {
        return new WaterloggedHulk(this);
    }
}
