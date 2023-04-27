package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FlamewarBrashVeteran extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FlamewarBrashVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.f.FlamewarStreetwiseOperative.class;

        // More Than Meets the Eye {B}{R}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{B}{R}"));

        // Sacrifice another artifact: Put a +1/+1 counter on Flamewar and convert it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new SacrificeTargetCost(filter)
        );
        ability.addEffect(new TransformSourceEffect().setText("and convert it"));
        this.addAbility(ability);

        // {1}, Discard your hand: Put all exiled cards you own with intel counters on them into your hand.
        ability = new SimpleActivatedAbility(new FlamewarBrashVeteranEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardHandCost());
        this.addAbility(ability);
    }

    private FlamewarBrashVeteran(final FlamewarBrashVeteran card) {
        super(card);
    }

    @Override
    public FlamewarBrashVeteran copy() {
        return new FlamewarBrashVeteran(this);
    }
}

class FlamewarBrashVeteranEffect extends OneShotEffect {

    FlamewarBrashVeteranEffect() {
        super(Outcome.Benefit);
        staticText = "put all exiled cards you own with intel counters on them into your hand";
    }

    private FlamewarBrashVeteranEffect(final FlamewarBrashVeteranEffect effect) {
        super(effect);
    }

    @Override
    public FlamewarBrashVeteranEffect copy() {
        return new FlamewarBrashVeteranEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = game
                .getExile()
                .getAllCards(game, source.getControllerId())
                .stream()
                .filter(card -> card.getCounters(game).containsKey(CounterType.INTEL))
                .collect(Collectors.toSet());
        return !cards.isEmpty() && player.moveCards(cards, Zone.HAND, source, game);
    }
}
