package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class Ghoultree extends CardImpl {

    public Ghoultree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Ghoultree costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(StaticFilters.FILTER_CARD_CREATURE)));
    }

    public Ghoultree(final Ghoultree card) {
        super(card);
    }

    @Override
    public Ghoultree copy() {
        return new Ghoultree(this);
    }
}
