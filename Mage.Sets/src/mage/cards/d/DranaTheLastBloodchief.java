package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DranaTheLastBloodchief extends CardImpl {

    public DranaTheLastBloodchief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Drana, the Last Bloodchief attacks, defending player chooses a nonlegendary creature card in your graveyard. You return that card to the battlefield with a +1/+1 counter on it. The creature is a Vampire in addition to its other types.
        this.addAbility(new AttacksTriggeredAbility(
                new DranaTheLastBloodchiefEffect(), false, "", SetTargetPointer.PLAYER
        ));
    }

    private DranaTheLastBloodchief(final DranaTheLastBloodchief card) {
        super(card);
    }

    @Override
    public DranaTheLastBloodchief copy() {
        return new DranaTheLastBloodchief(this);
    }
}

class DranaTheLastBloodchiefEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("nonlegendary creature card");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    DranaTheLastBloodchiefEffect() {
        super(Outcome.Benefit);
        staticText = "defending player chooses a nonlegendary creature card in your graveyard. " +
                "You return that card to the battlefield with an additional +1/+1 counter on it. " +
                "The creature is a Vampire in addition to its other types.";
    }

    private DranaTheLastBloodchiefEffect(final DranaTheLastBloodchiefEffect effect) {
        super(effect);
    }

    @Override
    public DranaTheLastBloodchiefEffect copy() {
        return new DranaTheLastBloodchiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || player == null
                || controller.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        player.choose(outcome, controller.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        game.addEffect(new DranaTheLastBloodchiefSubtypeEffect(card, game), source);
        game.addEffect(new DranaTheLastBloodchiefCounterEffect(card, game), source);
        return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class DranaTheLastBloodchiefSubtypeEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    DranaTheLastBloodchiefSubtypeEffect(Card card, Game game) {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        this.mor = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
    }


    private DranaTheLastBloodchiefSubtypeEffect(final DranaTheLastBloodchiefSubtypeEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = mor.getPermanent(game);
        if (creature != null) {
            creature.addSubType(game, SubType.VAMPIRE);
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public DranaTheLastBloodchiefSubtypeEffect copy() {
        return new DranaTheLastBloodchiefSubtypeEffect(this);
    }
}

class DranaTheLastBloodchiefCounterEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    DranaTheLastBloodchiefCounterEffect(Card card, Game game) {
        super(Duration.EndOfStep, Outcome.BoostCreature);
        this.mor = new MageObjectReference(card, game);
    }

    private DranaTheLastBloodchiefCounterEffect(final DranaTheLastBloodchiefCounterEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null && mor.refersTo(creature, game)) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public DranaTheLastBloodchiefCounterEffect copy() {
        return new DranaTheLastBloodchiefCounterEffect(this);
    }
}
