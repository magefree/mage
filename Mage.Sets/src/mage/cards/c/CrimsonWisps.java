package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class CrimsonWisps extends CardImpl {

    public CrimsonWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature becomes red and gains haste until end of turn.
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(
                ObjectColor.RED, Duration.EndOfTurn
        ).setText("target creature becomes red"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    public CrimsonWisps(final CrimsonWisps card) {
        super(card);
    }

    @Override
    public CrimsonWisps copy() {
        return new CrimsonWisps(this);
    }
}
