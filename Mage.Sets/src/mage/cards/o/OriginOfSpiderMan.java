package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spider21Token;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OriginOfSpiderMan extends CardImpl {

    public OriginOfSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a 2/1 green Spider creature token with reach.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new CreateTokenEffect(new Spider21Token()));

        // II -- Put a +1/+1 counter on target creature you control. It becomes a legendary Spider Hero in addition to its other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new Effects(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new OriginOfSpiderManEffect()
                ), new TargetControlledCreaturePermanent()
        );

        // III -- Target creature you control gains double strike until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()),
                new TargetControlledCreaturePermanent()
        );
        this.addAbility(sagaAbility);
    }

    private OriginOfSpiderMan(final OriginOfSpiderMan card) {
        super(card);
    }

    @Override
    public OriginOfSpiderMan copy() {
        return new OriginOfSpiderMan(this);
    }
}

class OriginOfSpiderManEffect extends ContinuousEffectImpl {

    OriginOfSpiderManEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "It becomes a legendary Spider Hero in addition to its other types";
    }

    private OriginOfSpiderManEffect(final OriginOfSpiderManEffect effect) {
        super(effect);
    }

    @Override
    public OriginOfSpiderManEffect copy() {
        return new OriginOfSpiderManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.addSuperType(game, SuperType.LEGENDARY);
        permanent.addSubType(game, SubType.SPIDER);
        permanent.addSubType(game, SubType.HERO);
        return true;
    }
}
