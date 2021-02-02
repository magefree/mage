
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class DispersalTechnician extends CardImpl {

    public DispersalTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Dispersal Technician enters the battlefield, you may return target artifact to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetPermanent(new FilterArtifactPermanent()));
        this.addAbility(ability);
    }

    private DispersalTechnician(final DispersalTechnician card) {
        super(card);
    }

    @Override
    public DispersalTechnician copy() {
        return new DispersalTechnician(this);
    }
}
