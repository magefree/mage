package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunBlessedGuardian extends TransformingDoubleFacedCard {

    public SunBlessedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{1}{W}",
                "Furnace-Blessed Conqueror",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.CLERIC}, "WR"
        );

        // Sun-Blessed Guardian
        this.getLeftHalfCard().setPT(2, 2);

        // {5}{R/P}: Transform Sun-Blessed Guardian. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{R/P}")));

        // Furnace-Blessed Conqueror
        this.getRightHalfCard().setPT(3, 3);

        // Whenever Furnace-Blessed Conqueror attacks, create a tapped and attacking token that's a copy of it. Put a +1/+1 counter on that token for each +1/+1 counter on Furnace-Blessed Conqueror. Sacrifice that token at the beginning of the next end step.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new SunBlessedGuardianEffect()));
    }

    private SunBlessedGuardian(final SunBlessedGuardian card) {
        super(card);
    }

    @Override
    public SunBlessedGuardian copy() {
        return new SunBlessedGuardian(this);
    }
}

class SunBlessedGuardianEffect extends OneShotEffect {

    SunBlessedGuardianEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of it. " +
                "Put a +1/+1 counter on that token for each +1/+1 counter on {this}. " +
                "Sacrifice that token at the beginning of the next end step";
    }

    private SunBlessedGuardianEffect(final SunBlessedGuardianEffect effect) {
        super(effect);
    }

    @Override
    public SunBlessedGuardianEffect copy() {
        return new SunBlessedGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, true
        );
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        int counters = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (counters < 1) {
            return true;
        }
        for (Permanent token : effect.getAddedPermanents()) {
            token.addCounters(CounterType.P1P1.createInstance(counters), source, game);
        }
        return true;
    }
}
