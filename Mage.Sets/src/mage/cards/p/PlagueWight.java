package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlagueWight extends CardImpl {

    public PlagueWight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Plague Wight becomes blocked, each creature blocking it gets -1/-1 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new PlagueWightEffect(), false));
    }

    private PlagueWight(final PlagueWight card) {
        super(card);
    }

    @Override
    public PlagueWight copy() {
        return new PlagueWight(this);
    }
}

class PlagueWightEffect extends OneShotEffect {

    PlagueWightEffect() {
        super(Outcome.Benefit);
        staticText = "each creature blocking it gets -1/-1 until end of turn.";
    }

    private PlagueWightEffect(final PlagueWightEffect effect) {
        super(effect);
    }

    @Override
    public PlagueWightEffect copy() {
        return new PlagueWightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new BlockingAttackerIdPredicate(source.getSourceId()));
        game.addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false), source);
        return true;
    }
}