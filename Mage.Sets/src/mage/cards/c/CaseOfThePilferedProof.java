package mage.cards.c;

import java.util.Map;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author DominionSpy
 */
public final class CaseOfThePilferedProof extends CardImpl {

    public CaseOfThePilferedProof(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.CASE);

        // Whenever a Detective enters the battlefield under your control and whenever a Detective you control is turned face up, put a +1/+1 counter on it.
        Ability initialAbility = new CaseOfThePilferedProofTriggeredAbility();
        // To solve -- You control three or more Detectives.
        Condition toSolveCondition = new PermanentsOnTheBattlefieldCondition(
                new FilterCreaturePermanent(SubType.DETECTIVE, "You control three or more Detectives"),
                ComparisonType.MORE_THAN, 2, true);
        // Solved -- If one or more tokens would be created under your control, those tokens plus a Clue token are created instead.
        Ability solvedAbility = new SimpleStaticAbility(new ConditionalReplacementEffect(
                new CaseOfThePilferedProofReplacementEffect(), SolvedSourceCondition.SOLVED));

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition,solvedAbility)
                .addHint(new CaseOfThePilferedProofHint(toSolveCondition)));
    }

    private CaseOfThePilferedProof(final CaseOfThePilferedProof card) {
        super(card);
    }

    @Override
    public CaseOfThePilferedProof copy() {
        return new CaseOfThePilferedProof(this);
    }
}

class CaseOfThePilferedProofTriggeredAbility extends TriggeredAbilityImpl {

    CaseOfThePilferedProofTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"));
        this.setTriggerPhrase("Whenever a Detective enters the battlefield under your control and whenever a Detective you control is turned face up, ");
    }

    private CaseOfThePilferedProofTriggeredAbility(final CaseOfThePilferedProofTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaseOfThePilferedProofTriggeredAbility copy() {
        return new CaseOfThePilferedProofTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case TURNED_FACE_UP:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.hasSubtype(SubType.DETECTIVE, game)
                && permanent.isControlledBy(this.getControllerId())) {
            getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }
}

class CaseOfThePilferedProofReplacementEffect extends ReplacementEffectImpl {

    CaseOfThePilferedProofReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more tokens would be created under your control, those tokens plus a Clue token are created instead";
    }

    private CaseOfThePilferedProofReplacementEffect(final CaseOfThePilferedProofReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CaseOfThePilferedProofReplacementEffect copy() {
        return new CaseOfThePilferedProofReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            Map<Token, Integer> tokens = tokenEvent.getTokens();
            tokens.put(new ClueArtifactToken(), 1);
        }
        return false;
    }
}

class CaseOfThePilferedProofHint extends CaseSolvedHint {

    CaseOfThePilferedProofHint(Condition condition) {
        super(condition);
    }

    private CaseOfThePilferedProofHint(final CaseOfThePilferedProofHint hint) {
        super(hint);
    }

    @Override
    public CaseOfThePilferedProofHint copy() {
        return new CaseOfThePilferedProofHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability ability) {
        int detectives = game.getBattlefield()
                .count(new FilterControlledCreaturePermanent(SubType.DETECTIVE),
                        ability.getControllerId(), ability, game);
        return "Detectives: " + detectives + " (need 3).";
    }
}
