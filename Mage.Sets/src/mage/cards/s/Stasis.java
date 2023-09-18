
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.SkipUntapStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth, edited by L_J
 */
public final class Stasis extends CardImpl {

    public Stasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Players skip their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapStepEffect()));

        // At the beginning of your upkeep, sacrifice Stasis unless you pay {U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{U}")), TargetController.YOU, false));

    }

    private Stasis(final Stasis card) {
        super(card);
    }

    @Override
    public Stasis copy() {
        return new Stasis(this);
    }
}
