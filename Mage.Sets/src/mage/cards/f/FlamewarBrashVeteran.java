package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.*;
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
public final class FlamewarBrashVeteran extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FlamewarBrashVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{1}{B}{R}",
                "Flamewar, Streetwise Operative",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "BR"
        );

        // Flamewar, Brash Veteran
        this.getLeftHalfCard().setPT(3, 2);

        // More Than Meets the Eye {B}{R}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{B}{R}"));

        // Sacrifice another artifact: Put a +1/+1 counter on Flamewar and convert it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new SacrificeTargetCost(filter)
        );
        ability.addEffect(new TransformSourceEffect().setText("and convert it"));
        this.getLeftHalfCard().addAbility(ability);

        // {1}, Discard your hand: Put all exiled cards you own with intel counters on them into your hand.
        ability = new SimpleActivatedAbility(new FlamewarBrashVeteranEffect(), new GenericManaCost(1));
        ability.addCost(new DiscardHandCost());
        this.getLeftHalfCard().addAbility(ability);

        // Flamewar, Streetwise Operative
        this.getRightHalfCard().setPT(2, 1);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Flamewar deals combat damage to a player, exile that many cards from the top of your library face down. Put an intel counter on each of them. Convert Flamewar.
        Ability backAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new FlamewarStreetwiseOperativeEffect(), false);
        backAbility.addEffect(new TransformSourceEffect().setText("convert {this}"));
        this.getRightHalfCard().addAbility(backAbility);
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
                .getCardsOwned(game, source.getControllerId())
                .stream()
                .filter(card -> card.getCounters(game).containsKey(CounterType.INTEL))
                .collect(Collectors.toSet());
        return !cards.isEmpty() && player.moveCards(cards, Zone.HAND, source, game);
    }
}

class FlamewarStreetwiseOperativeEffect extends OneShotEffect {

    FlamewarStreetwiseOperativeEffect() {
        super(Outcome.Benefit);
        staticText = "exile that many cards from the top of your library face down. " +
                "Put an intel counter on each of them";
    }

    private FlamewarStreetwiseOperativeEffect(final FlamewarStreetwiseOperativeEffect effect) {
        super(effect);
    }

    @Override
    public FlamewarStreetwiseOperativeEffect copy() {
        return new FlamewarStreetwiseOperativeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int damage = (Integer) getValue("damage");
        if (player == null || damage < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, damage));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        cards.getCards(game).forEach(card -> {
            card.setFaceDown(true, game);
            card.addCounters(CounterType.INTEL.createInstance(), source, game);
        });
        return true;
    }
}
