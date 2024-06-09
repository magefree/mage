
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class KyrenArchive extends CardImpl {

    public KyrenArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your upkeep, you may exile the top card of your library face down.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileCardsFromTopOfLibraryControllerEffect(1, true, true),
                TargetController.YOU,
                true)
        );

        // {5}, Discard your hand, Sacrifice Kyren Archive: Put all cards exiled with Kyren Archive into their owner's hand.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ReturnFromExileForSourceEffect(Zone.HAND).withText(true, false, true),
                new GenericManaCost(5)
        );
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KyrenArchive(final KyrenArchive card) {
        super(card);
    }

    @Override
    public KyrenArchive copy() {
        return new KyrenArchive(this);
    }
}
