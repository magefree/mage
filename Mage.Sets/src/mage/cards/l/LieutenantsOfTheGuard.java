package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
 */
public final class LieutenantsOfTheGuard extends CardImpl {

    public LieutenantsOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Council's dilemma</i> &mdash; When Lieutenants of the Guard enters the battlefield, starting with you,
        // each player votes for strength or numbers. Put a +1/+1 counter on Lieutenants of the Guard for each
        // strength vote and put a 1/1 white Soldier creature token onto the battlefield for each numbers vote.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LieutenantsOfTheGuardEffect(), false)
                .setAbilityWord(AbilityWord.COUNCILS_DILEMMA)
        );
    }

    private LieutenantsOfTheGuard(final LieutenantsOfTheGuard card) {
        super(card);
    }

    @Override
    public LieutenantsOfTheGuard copy() {
        return new LieutenantsOfTheGuard(this);
    }
}

class LieutenantsOfTheGuardEffect extends OneShotEffect {

    LieutenantsOfTheGuardEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for strength or numbers. " +
                "Put a +1/+1 counter on {this} for each strength vote " +
                "and create a 1/1 white Soldier creature token for each numbers vote.";
    }

    private LieutenantsOfTheGuardEffect(final LieutenantsOfTheGuardEffect effect) {
        super(effect);
    }

    @Override
    public LieutenantsOfTheGuardEffect copy() {
        return new LieutenantsOfTheGuardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Outcome.Benefit - AI will boost all the time (Strength choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Strength (+1/+1 counter)", "Numbers (1/1 token)", Outcome.Benefit);
        vote.doVotes(source, game);

        int strengthCount = vote.getVoteCount(true);
        int numbersCount = vote.getVoteCount(false);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (strengthCount > 0 && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(strengthCount), source.getControllerId(), source, game);
        }
        if (numbersCount > 0) {
            new SoldierToken().putOntoBattlefield(numbersCount, game, source, source.getControllerId());
        }
        return strengthCount + numbersCount > 0;
    }
}
