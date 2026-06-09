package mage.cards.t;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwoHeadedHunter extends AdventureCard {

    public TwoHeadedHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT}, "{4}{R}",
                "Twice the Rage",
                new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Two-Headed Hunter
        this.getLeftHalfCard().setPT(5, 4);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility());

        // Twice the Rage
        // Target creature gains double strike until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private TwoHeadedHunter(final TwoHeadedHunter card) {
        super(card);
    }

    @Override
    public TwoHeadedHunter copy() {
        return new TwoHeadedHunter(this);
    }
}
