
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Loki
 */
public final class PatchworkGnomes extends CardImpl {

    public PatchworkGnomes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GNOME);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Discard a card: Regenerate Patchwork Gnomes.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new DiscardTargetCost(new TargetCardInHand(new FilterCard("a card")))));
    }

    private PatchworkGnomes(final PatchworkGnomes card) {
        super(card);
    }

    @Override
    public PatchworkGnomes copy() {
        return new PatchworkGnomes(this);
    }
}
