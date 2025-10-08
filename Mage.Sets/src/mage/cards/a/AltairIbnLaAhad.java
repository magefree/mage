package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AltairIbnLaAhad extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Assassin creature card from your graveyard");

    static {
        filter.add(SubType.ASSASSIN.getPredicate());
    }

    public AltairIbnLaAhad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Altair Ibn-La'Ahad attacks, exile up to one target Assassin creature card from your graveyard with a memory counter on it. Then for each creature card you own in exile with a memory counter on it, create a tapped and attacking token that's a copy of it. Exile those tokens at end of combat.
        Ability ability = new AttacksTriggeredAbility(new AltairIbnLaAhadExileEffect());
        ability.addEffect(new AltairIbnLaAhadTokenEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        this.addAbility(ability);
    }

    private AltairIbnLaAhad(final AltairIbnLaAhad card) {
        super(card);
    }

    @Override
    public AltairIbnLaAhad copy() {
        return new AltairIbnLaAhad(this);
    }
}

class AltairIbnLaAhadExileEffect extends OneShotEffect {

    AltairIbnLaAhadExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target Assassin creature card from your graveyard with a memory counter on it";
    }

    private AltairIbnLaAhadExileEffect(final AltairIbnLaAhadExileEffect effect) {
        super(effect);
    }

    @Override
    public AltairIbnLaAhadExileEffect copy() {
        return new AltairIbnLaAhadExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        card.addCounters(CounterType.MEMORY.createInstance(), source, game);
        return true;
    }
}

class AltairIbnLaAhadTokenEffect extends OneShotEffect {

    AltairIbnLaAhadTokenEffect() {
        super(Outcome.Benefit);
        staticText = "Then for each creature card you own in exile with a memory counter on it, " +
                "create a tapped and attacking token that's a copy of it. Exile those tokens at end of combat";
    }

    private AltairIbnLaAhadTokenEffect(final AltairIbnLaAhadTokenEffect effect) {
        super(effect);
    }

    @Override
    public AltairIbnLaAhadTokenEffect copy() {
        return new AltairIbnLaAhadTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Card> cards = game
                .getExile()
                .getCardsOwned(game, source.getControllerId())
                .stream()
                .filter(card -> card.getCounters(game).containsKey(CounterType.MEMORY))
                .filter(card -> card.isCreature(game))
                .collect(Collectors.toSet());
        if (cards.isEmpty()) {
            return false;
        }
        Set<Permanent> permanents = new HashSet<>();
        for (Card card : cards) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                    source.getControllerId(), null,
                    false, 1, true, true
            );
            effect.setSavedPermanent(new PermanentCard(card, source.getControllerId(), game));
            effect.apply(game, source);
            permanents.addAll(effect.getAddedPermanents());
        }
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new ExileTargetEffect("exile those tokens")
                        .setTargetPointer(new FixedTargets(permanents, game))
        ), source);
        return true;
    }
}
