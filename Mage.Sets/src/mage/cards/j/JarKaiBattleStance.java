package mage.cards.j;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class JarKaiBattleStance extends CardImpl {

    public JarKaiBattleStance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gains double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // If that creature is a Jedi or Sith, it also gains trample until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(new SourceHasSubtypeCondition(SubType.JEDI, SubType.SITH)),
                "If that creature is a Jedi or Sith, it also gains trample until end of turn"));
    }

    private JarKaiBattleStance(final JarKaiBattleStance card) {
        super(card);
    }

    @Override
    public JarKaiBattleStance copy() {
        return new JarKaiBattleStance(this);
    }
}
