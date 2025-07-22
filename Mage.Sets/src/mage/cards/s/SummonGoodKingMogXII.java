package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MoogleToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonGoodKingMogXII extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOOGLE, "other Moogle you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SummonGoodKingMogXII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.MOOGLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I - Create two 1/2 white Moogle creature tokens with lifelink.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new MoogleToken(), 2)
        );

        // II, III - Whenever you cast a noncreature spell this turn, create a token that's a copy of a non-Saga token you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CreateDelayedTriggeredAbilityEffect(new SummonGoodKingMogXIITriggeredAbility())
        );

        // IV - Put two +1/+1 counters on each other Moogle you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter)
        );
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private SummonGoodKingMogXII(final SummonGoodKingMogXII card) {
        super(card);
    }

    @Override
    public SummonGoodKingMogXII copy() {
        return new SummonGoodKingMogXII(this);
    }
}

class SummonGoodKingMogXIITriggeredAbility extends DelayedTriggeredAbility {

    SummonGoodKingMogXIITriggeredAbility() {
        super(new SummonGoodKingMogXIIEffect(), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever you cast a noncreature spell this turn, ");
    }

    private SummonGoodKingMogXIITriggeredAbility(final SummonGoodKingMogXIITriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SummonGoodKingMogXIITriggeredAbility copy() {
        return new SummonGoodKingMogXIITriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && !spell.isCreature(game);
    }
}

class SummonGoodKingMogXIIEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("non-Saga token you control");

    static {
        filter.add(Predicates.not(SubType.SAGA.getPredicate()));
        filter.add(TokenPredicate.TRUE);
    }

    SummonGoodKingMogXIIEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of a non-Saga token you control";
    }

    private SummonGoodKingMogXIIEffect(final SummonGoodKingMogXIIEffect effect) {
        super(effect);
    }

    @Override
    public SummonGoodKingMogXIIEffect copy() {
        return new SummonGoodKingMogXIIEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(filter, source, game, 1)) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
    }
}
