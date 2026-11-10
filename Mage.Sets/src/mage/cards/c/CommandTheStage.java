package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.OpponentDealtNoncombatDamageCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CadetToken;
import mage.watchers.common.NoncombatDamageToPlayersWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class CommandTheStage extends CardImpl {

    public CommandTheStage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Create a 2/2 colorless Wizard Soldier creature token named Cadet, then put a +1/+1 counter on each other Wizard token you control.
        this.getSpellAbility().addEffect(new CommandTheStageEffect());

        // At the beginning of each upkeep, if an opponent was dealt noncombat damage last turn, return this card from your graveyard to your hand.
        this.addAbility(
            new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD, TargetController.ANY,
                new ReturnSourceFromGraveyardToHandEffect(),
                false
            ).withInterveningIf(OpponentDealtNoncombatDamageCondition.LAST_TURN),
            new NoncombatDamageToPlayersWatcher()
        );
    }

    private CommandTheStage(final CommandTheStage card) {
        super(card);
    }

    @Override
    public CommandTheStage copy() {
        return new CommandTheStage(this);
    }
}

class CommandTheStageEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WIZARD);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    CommandTheStageEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/2 colorless Wizard Soldier creature token named Cadet, "
                + "then put a +1/+1 counter on each other Wizard token you control";
    }

    private CommandTheStageEffect(final CommandTheStageEffect effect) {
        super(effect);
    }

    @Override
    public CommandTheStageEffect copy() {
        return new CommandTheStageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CadetToken token = new CadetToken();
        token.putOntoBattlefield(1, game, source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!token.getLastAddedTokenIds().contains(permanent.getId())) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}
