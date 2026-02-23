package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BrasssTunnelGrinder extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BORE, 3);

    private static final FilterSpell filter = new FilterSpell("a permanent spell");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public BrasssTunnelGrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{R}",
                "Tecutlan, the Searing Rift",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{SubType.CAVE}, "");

        // Brass's Tunnel-Grinder
        // When Brass's Tunnel-Grinder enters the battlefield, discard any number of cards, then draw that many cards plus one.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new BrasssTunnelGrinderEffect()));

        // At the beginning of your end step, if you descended this turn, put a bore counter on Brass's Tunnel-Grinder. Then if there are three or more bore counters on it, remove those counters and transform it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new AddCountersSourceEffect(CounterType.BORE.createInstance()),
                false, DescendedThisTurnCondition.instance
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.BORE), condition,
                "Then if there are three or more bore counters on it, remove those counters and transform it"
        ).addEffect(new TransformSourceEffect()));
        ability.addHint(DescendedThisTurnCount.getHint());
        ability.addWatcher(new DescendedWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Tecutlan, the Searing Rift
        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());

        // Whenever you cast a permanent spell using mana produced by Tecutlan, the Searing Rift, discover X, where X is that spell's mana value.
        this.getRightHalfCard().addAbility(new CastSpellPaidBySourceTriggeredAbility(
                new TecutlanTheSearingRiftEffect(),
                filter, true
        ));
    }

    private BrasssTunnelGrinder(final BrasssTunnelGrinder card) {
        super(card);
    }

    @Override
    public BrasssTunnelGrinder copy() {
        return new BrasssTunnelGrinder(this);
    }
}

class BrasssTunnelGrinderEffect extends OneShotEffect {

    BrasssTunnelGrinderEffect() {
        super(Outcome.DrawCard);
        staticText = "discard any number of cards, then draw that many cards plus one";
    }

    private BrasssTunnelGrinderEffect(final BrasssTunnelGrinderEffect effect) {
        super(effect);
    }

    @Override
    public BrasssTunnelGrinderEffect copy() {
        return new BrasssTunnelGrinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int dicarded = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        player.drawCards(1 + dicarded, source, game);
        return true;
    }
}

class TecutlanTheSearingRiftEffect extends OneShotEffect {

    TecutlanTheSearingRiftEffect() {
        super(Outcome.Benefit);
        staticText = "discover X, where X is that spell's mana value";
    }

    private TecutlanTheSearingRiftEffect(final TecutlanTheSearingRiftEffect effect) {
        super(effect);
    }

    @Override
    public TecutlanTheSearingRiftEffect copy() {
        return new TecutlanTheSearingRiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        int mv = spell == null ? 0 : Math.max(0, spell.getManaValue());

        DiscoverEffect.doDiscover(controller, mv, game, source);
        return true;
    }

}
