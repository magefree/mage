
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class SalvageTitan extends CardImpl {

    public SalvageTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // You may sacrifice three artifacts rather than pay Salvage Titan's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledArtifactPermanent("artifacts"), true))));

        // Exile three artifact cards from your graveyard: Return Salvage Titan from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ExileFromGraveCost(new TargetCardInYourGraveyard(3, StaticFilters.FILTER_CARD_ARTIFACTS))));
    }

    private SalvageTitan(final SalvageTitan card) {
        super(card);
    }

    @Override
    public SalvageTitan copy() {
        return new SalvageTitan(this);
    }
}
