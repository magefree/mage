package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquirrelToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CometStellarPup extends CardImpl {

    public CometStellarPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.COMET);
        this.setStartingLoyalty(5);

        // 0: Roll a six-sided die.
        // 1 or 2 -- [+2] , then create two 1/1 green Squirrel creature tokens. They gain haste until end of turn.
        // 3 -- [-1], then return a card with mana value 2 or less from your graveyard to your hand.
        // 4 or 5 -- Comet, Stellar Pup deals damage equal to the number of loyalty counters on him to a creature or player, then [-2].
        // 6 -- [+1], and you may activate Comet, Stellar Pup's loyalty ability two more times this turn.
        this.addAbility(new LoyaltyAbility(new CometStellarPupAbility(), 0));
    }

    private CometStellarPup(final CometStellarPup card) {
        super(card);
    }

    @Override
    public CometStellarPup copy() {
        return new CometStellarPup(this);
    }
}

class CometStellarPupAbility extends OneShotEffect {

    private static final FilterCard filterCard =
            new FilterCard("card with mana value 2 or less from your graveyard");

    static {
        filterCard.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    CometStellarPupAbility() {
        super(Outcome.Benefit);
        staticText = "Roll a six-sided die.<br>"
                + "1 or 2 &mdash; [+2], then create two 1/1 green Squirrel creature tokens. They gain haste until end of turn.<br>"
                + "3 &mdash; [-1], then return a card with mana value 2 or less from your graveyard to your hand.<br>"
                + "4 or 5 &mdash; {this} deals damage equal to the number of loyalty counters on him to a creature or player, then [-2].<br>"
                + "6 &mdash; [+1], and you may activate Comet, Stellar Pup's loyalty ability two more times this turn.";
    }

    private CometStellarPupAbility(final CometStellarPupAbility effect) {
        super(effect);
    }

    @Override
    public CometStellarPupAbility copy() {
        return new CometStellarPupAbility(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        if (result == 1 || result == 2) {
            // [+2]
            new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(2))
                    .apply(game, source);

            // Create two 1/1 green Squirrel creature tokens.
            Token token = new SquirrelToken();
            token.putOntoBattlefield(2, game, source);

            // They gain haste until end of turn.
            game.addEffect(new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTargets(token, game)), source);
        } else if (result == 3) {
            // [-1]
            new RemoveCounterSourceEffect(CounterType.LOYALTY.createInstance(1))
                    .apply(game, source);

            // return a card with mana value 2 or less from your graveyard to your hand.
            TargetCard target = new TargetCardInYourGraveyard(filterCard);
            target.withNotTarget(true);
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return true;
            }
            player.choose(outcome, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.moveCards(card, Zone.HAND, source, game);
            }
        } else if (result == 4 || result == 5) {
            // Comet, Stellar Pup deals damage equal to the number of loyalty counters on him to a creature or player
            TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
            target.withNotTarget(true);
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return true;
            }
            player.choose(Outcome.Damage, target, source, game);
            new DamageTargetEffect(new CountersSourceCount(CounterType.LOYALTY))
                    .setTargetPointer(new FixedTarget(target.getFirstTarget()))
                    .apply(game, source);

            // [−2]
            new RemoveCounterSourceEffect(CounterType.LOYALTY.createInstance(2))
                    .apply(game, source);
        } else if (result == 6) {
            //[+1]
            new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(1))
                    .apply(game, source);

            // You may activate Comet, Stellar Pup’s loyalty ability two more times this turn.
            Permanent comet = source.getSourcePermanentIfItStillExists(game);
            if (comet != null) {
                game.addEffect(
                        new CometStellarPupContinuousEffect(new MageObjectReference(comet, game)),
                        source
                );
            }
        }
        return true;
    }
}

class CometStellarPupContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference cometMOR;

    CometStellarPupContinuousEffect(MageObjectReference cometMOR) {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.cometMOR = cometMOR;
    }

    private CometStellarPupContinuousEffect(final CometStellarPupContinuousEffect effect) {
        super(effect);
        this.cometMOR = effect.cometMOR;
    }

    @Override
    public CometStellarPupContinuousEffect copy() {
        return new CometStellarPupContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent comet = cometMOR.getPermanent(game);
        if (comet != null) {
            comet.incrementLoyaltyActivationsAvailable();
            comet.incrementLoyaltyActivationsAvailable();
            return true;
        }
        return false;
    }
}
