package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class FurnaceBlessedConqueror extends CardImpl {

    public FurnaceBlessedConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Whenever Furnace-Blessed Conqueror attacks, create a tapped and attacking token that's a copy of it. Put a +1/+1 counter on that token for each +1/+1 counter on Furnace-Blessed Conqueror. Sacrifice that token at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new FurnaceBlessedConquerorEffect()));
    }

    private FurnaceBlessedConqueror(final FurnaceBlessedConqueror card) {
        super(card);
    }

    @Override
    public FurnaceBlessedConqueror copy() {
        return new FurnaceBlessedConqueror(this);
    }
}

class FurnaceBlessedConquerorEffect extends OneShotEffect {

    FurnaceBlessedConquerorEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of it. " +
                "Put a +1/+1 counter on that token for each +1/+1 counter on {this}. " +
                "Sacrifice that token at the beginning of the next end step";
    }

    private FurnaceBlessedConquerorEffect(final FurnaceBlessedConquerorEffect effect) {
        super(effect);
    }

    @Override
    public FurnaceBlessedConquerorEffect copy() {
        return new FurnaceBlessedConquerorEffect(this);
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
