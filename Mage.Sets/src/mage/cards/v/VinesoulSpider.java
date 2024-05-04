package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.PutRandomCardFromLibraryIntoGraveyardEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import java.util.UUID;

/**
 * @author karapuzz14
 */
public final class VinesoulSpider extends CardImpl {

    public VinesoulSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());

        // At the beginning of your end step, put a random land card from your library into your graveyard.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new PutRandomCardFromLibraryIntoGraveyardEffect(StaticFilters.FILTER_CARD_LAND), TargetController.YOU, false
        ));

    }

    private VinesoulSpider(final VinesoulSpider card) {
        super(card);
    }

    @Override
    public VinesoulSpider copy() {
        return new VinesoulSpider(this);
    }
}




