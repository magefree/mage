
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author TheElk801
 */
public final class VoltaicServant extends CardImpl {

    public VoltaicServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, untap target artifact.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new UntapTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private VoltaicServant(final VoltaicServant card) {
        super(card);
    }

    @Override
    public VoltaicServant copy() {
        return new VoltaicServant(this);
    }
}
