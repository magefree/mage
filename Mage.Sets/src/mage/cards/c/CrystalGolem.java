
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class CrystalGolem extends CardImpl {

    public CrystalGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, Crystal Golem phases out.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new PhaseOutSourceEffect(), TargetController.YOU, false));
    }

    private CrystalGolem(final CrystalGolem card) {
        super(card);
    }

    @Override
    public CrystalGolem copy() {
        return new CrystalGolem(this);
    }
}
