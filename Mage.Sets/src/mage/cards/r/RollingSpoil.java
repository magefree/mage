
package mage.cards.r;

import java.util.UUID;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class RollingSpoil extends CardImpl {

    public RollingSpoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");

        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        
        // If {B} was spent to cast Rolling Spoil, all creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostAllEffect(-1, -1, Duration.EndOfTurn),
                new ManaWasSpentCondition(ColoredManaSymbol.B), "If {B} was spent to cast this spell, all creatures get -1/-1 until end of turn"));
    }

    private RollingSpoil(final RollingSpoil card) {
        super(card);
    }

    @Override
    public RollingSpoil copy() {
        return new RollingSpoil(this);
    }
}
