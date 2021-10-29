package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.game.permanent.token.ElfKnightToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AssureAssemble extends SplitCard {

    public AssureAssemble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G/W}{G/W}", "{4}{G}{W}", SpellAbilityType.SPLIT);

        // Assure
        // Put a +1/+1 counter on target creature. It gains indestructible until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.EndOfTurn
                ).setText("That creature gains indestructible until end of turn.")
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetCreaturePermanent()
        );

        // Assemble
        // Create three 2/2 green and white Elf Knight creature tokens with vigilance.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new CreateTokenEffect(new ElfKnightToken(), 3)
        );
    }

    private AssureAssemble(final AssureAssemble card) {
        super(card);
    }

    @Override
    public AssureAssemble copy() {
        return new AssureAssemble(this);
    }
}
