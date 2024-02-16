package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraLegacyOfFire extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER, null
    );
    private static final Hint hint = new ValueHint("Planeswalkers you control", xValue);

    public ChandraLegacyOfFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(3);

        // At the beginning of your end step, Chandra, Legacy of Fire deals X damage to each opponent, where X is the number of planeswalkers you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DamagePlayersEffect(
                        Outcome.Benefit, xValue, TargetController.OPPONENT
                ), TargetController.YOU, false
        ).addHint(hint));

        // +1: Add {R} for each planeswalker you control.
        this.addAbility(new LoyaltyAbility(new ChandraLegacyOfFireManaEffect(), 1));

        // 0: Remove a loyalty counter from each of any number of permanents you control. Exile that many cards from the top of your library. You may play them this turn.
        this.addAbility(new LoyaltyAbility(new ChandraLegacyOfFireExileEffect(), 0));
    }

    private ChandraLegacyOfFire(final ChandraLegacyOfFire card) {
        super(card);
    }

    @Override
    public ChandraLegacyOfFire copy() {
        return new ChandraLegacyOfFire(this);
    }
}

class ChandraLegacyOfFireManaEffect extends OneShotEffect {

    ChandraLegacyOfFireManaEffect() {
        super(Outcome.Benefit);
        staticText = "add {R} for each planeswalker you control";
    }

    private ChandraLegacyOfFireManaEffect(final ChandraLegacyOfFireManaEffect effect) {
        super(effect);
    }

    @Override
    public ChandraLegacyOfFireManaEffect copy() {
        return new ChandraLegacyOfFireManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER, source.getControllerId(), source, game);
        if (count > 0) {
            player.getManaPool().addMana(Mana.RedMana(count), game, source);
            return true;
        }
        return false;
    }
}

class ChandraLegacyOfFireExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CounterType.LOYALTY.getPredicate());
    }

    ChandraLegacyOfFireExileEffect() {
        super(Outcome.Benefit);
        staticText = "remove a loyalty counter from each of any number of permanents you control. " +
                "Exile that many cards from the top of your library. You may play them this turn";
    }

    private ChandraLegacyOfFireExileEffect(final ChandraLegacyOfFireExileEffect effect) {
        super(effect);
    }

    @Override
    public ChandraLegacyOfFireExileEffect copy() {
        return new ChandraLegacyOfFireExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            permanent.removeCounters(CounterType.LOYALTY.createInstance(), source, game);
            count++;
        }
        if (count < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, count));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}
