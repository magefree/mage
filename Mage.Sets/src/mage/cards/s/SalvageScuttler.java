
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class SalvageScuttler extends CardImpl {

    public SalvageScuttler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Salvage Scuttler attacks, return an artifact you control to its owner's hand.
        Ability ability = new AttacksTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact you control")));
        this.addAbility(ability);
    }

    private SalvageScuttler(final SalvageScuttler card) {
        super(card);
    }

    @Override
    public SalvageScuttler copy() {
        return new SalvageScuttler(this);
    }
}
