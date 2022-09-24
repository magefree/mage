package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Elemental21TrampleHasteToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author weirddan455
 */
public final class LagomosHandOfHatred extends CardImpl {

    public LagomosHandOfHatred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, create a 2/1 red Elemental creature token with trample and haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new LagomosHandOfHatredEffect(), TargetController.YOU, false));

        // {T}: Search your library for a card, put it into your hand, then shuffle. Activate only if five or more creatures died this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary()),
                new TapSourceCost(),
                LagomosHandOfHatredCondition.instance
        ), new CreaturesDiedWatcher());
    }

    private LagomosHandOfHatred(final LagomosHandOfHatred card) {
        super(card);
    }

    @Override
    public LagomosHandOfHatred copy() {
        return new LagomosHandOfHatred(this);
    }
}

class LagomosHandOfHatredEffect extends OneShotEffect {

    public LagomosHandOfHatredEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 2/1 red Elemental creature token with trample and haste. Sacrifice it at the beginning of the next end step.";
    }

    private LagomosHandOfHatredEffect(final LagomosHandOfHatredEffect effect) {
        super(effect);
    }

    @Override
    public LagomosHandOfHatredEffect copy() {
        return new LagomosHandOfHatredEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new Elemental21TrampleHasteToken();
        token.putOntoBattlefield(1, game, source);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeTargetEffect("sacrifice it")
                        .setTargetPointer(new FixedTarget(permanent, game))), source);
            }
        }
        return true;
    }
}

enum LagomosHandOfHatredCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        return watcher != null && watcher.getAmountOfCreaturesDiedThisTurn() >= 5;
    }

    @Override
    public String toString() {
        return "five or more creatures died this turn";
    }
}
