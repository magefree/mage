
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseLandTypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllOfChosenSubtypeEffect;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class Shimmer extends CardImpl {

    public Shimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");

        // As Shimmer enters the battlefield, choose a land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseLandTypeEffect(Outcome.Detriment)));

        // Each land of the chosen type has phasing.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllOfChosenSubtypeEffect(PhasingAbility.getInstance(), Duration.WhileOnBattlefield,
                        new FilterLandPermanent("Each land of the chosen type"))));
    }

    private Shimmer(final Shimmer card) {
        super(card);
    }

    @Override
    public Shimmer copy() {
        return new Shimmer(this);
    }
}
