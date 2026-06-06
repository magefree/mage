
package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PollenShieldHare extends AdventureCard {
    
    public PollenShieldHare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.RABBIT}, "{1}{W}",
                "Hare Raising",
                new CardType[]{CardType.SORCERY}, "{G}");

        // Pollen-Shield Hare
        this.getLeftHalfCard().setPT(2, 2);

        // Creature tokens you control get +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS, false)
        ));

        // Hare Raising
        // Target creature you control gains vigilance and gets +X/+X until end of turn, where X is the number of creatures you control.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                        .setText("target creature you control gains vigilance")
        );
        this.getRightHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL)
                        .setText("and gets +X/+X until end of turn, where X is the number of creatures you control")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getRightHalfCard().getSpellAbility().addHint(CreaturesYouControlHint.instance);

        finalizeCard();
    }

    private PollenShieldHare(final PollenShieldHare card) {
        super(card);
    }

    @Override
    public PollenShieldHare copy() {
        return new PollenShieldHare(this);
    }
}
