package mage.cards.j;

import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JumpScare extends CardImpl {

    public JumpScare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Until end of turn, target creature gets +2/+2, gains flying, and becomes a Horror enchantment creature in addition to its other types.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2)
                .setText("until end of turn, target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText(", gains flying"));
        this.getSpellAbility().addEffect(new AddCardSubTypeTargetEffect(
                SubType.HORROR, Duration.EndOfTurn
        ).setText(", and becomes"));
        this.getSpellAbility().addEffect(new AddCardTypeTargetEffect(
                Duration.EndOfTurn, CardType.ENCHANTMENT, CardType.CREATURE
        ).setText(" a Horror enchantment creature in addition to its other types"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private JumpScare(final JumpScare card) {
        super(card);
    }

    @Override
    public JumpScare copy() {
        return new JumpScare(this);
    }
}
