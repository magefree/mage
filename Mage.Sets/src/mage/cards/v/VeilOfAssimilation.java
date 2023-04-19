package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeilOfAssimilation extends CardImpl {

    public VeilOfAssimilation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Whenever Veil of Assimilation or another artifact enters the battlefield under your control, target creature you control gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(1, 1).setText("target creature you control gets +1/+1"),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        );
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gains vigilance until end of turn"));
        this.addAbility(ability);
    }

    private VeilOfAssimilation(final VeilOfAssimilation card) {
        super(card);
    }

    @Override
    public VeilOfAssimilation copy() {
        return new VeilOfAssimilation(this);
    }
}
