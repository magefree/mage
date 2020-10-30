package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LieutenantsOfTheGuardDilemmaEffect(), false, "<i>Council's dilemma</i> &mdash; "));
    }

    public LieutenantsOfTheGuard(final LieutenantsOfTheGuard card) {
        super(card);
    }

    @Override
    public LieutenantsOfTheGuard copy() {
        return new LieutenantsOfTheGuard(this);
    }
}

class LieutenantsOfTheGuardDilemmaEffect extends CouncilsDilemmaVoteEffect {

    public LieutenantsOfTheGuardDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for strength or numbers. Put a +1/+1 counter on {this} for each strength vote and put a 1/1 white Soldier creature token onto the battlefield for each numbers vote.";
    }

    public LieutenantsOfTheGuardDilemmaEffect(final LieutenantsOfTheGuardDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) {
            return false;
        }

        this.vote("strength", "numbers", controller, game, source);

        Permanent permanent = game.getPermanent(source.getSourceId());

        //Strength Votes
        //If strength received zero votes or the permanent is no longer on the battlefield, do not attempt to put P1P1 counters on it.
        if (voteOneCount > 0 && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(voteOneCount), source, game);
        }

        //Numbers Votes
        if (voteTwoCount > 0) {
            Effect tokenEffect = new CreateTokenEffect(new SoldierToken(), voteTwoCount);
            tokenEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public LieutenantsOfTheGuardDilemmaEffect copy() {
        return new LieutenantsOfTheGuardDilemmaEffect(this);
    }
}
