package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianaUntouchedByDeath extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.ZOMBIE, "Zombies you control");

    public LilianaUntouchedByDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(4);

        // +1: Put the top three cards of your library into your graveyard. If at least one of them is a Zombie card, each opponent loses 2 life and you gain 2 life.
        this.addAbility(new LoyaltyAbility(new LilianaUntouchedByDeathEffect(), 1));

        // -2: Target creature gets -X/-X until end of turn, where X is the number of Zombies you control.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, -1);
        Ability ability = new LoyaltyAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true)
                        .setText("target creature gets -X/-X until end of turn, "
                                + "where X is the number of Zombies you control"), -2
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -3: You may cast Zombie cards from your graveyard this turn.
        this.addAbility(new LoyaltyAbility(new LilianaUntouchedByDeathGraveyardEffect(), -3));
    }

    private LilianaUntouchedByDeath(final LilianaUntouchedByDeath card) {
        super(card);
    }

    @Override
    public LilianaUntouchedByDeath copy() {
        return new LilianaUntouchedByDeath(this);
    }
}

class LilianaUntouchedByDeathEffect extends OneShotEffect {

    public LilianaUntouchedByDeathEffect() {
        super(Outcome.Benefit);
        this.staticText = "mill three cards. If at least one of them is a Zombie card, each opponent loses 2 life and you gain 2 life";
    }

    public LilianaUntouchedByDeathEffect(final LilianaUntouchedByDeathEffect effect) {
        super(effect);
    }

    @Override
    public LilianaUntouchedByDeathEffect copy() {
        return new LilianaUntouchedByDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player
                .millCards(3, source, game)
                .getCards(game)
                .stream()
                .anyMatch(card -> card.hasSubtype(SubType.ZOMBIE, game))) {
            new LoseLifeOpponentsEffect(2).apply(game, source);
            player.gainLife(2, game, source);
        }
        return true;
    }
}

class LilianaUntouchedByDeathGraveyardEffect extends AsThoughEffectImpl {

    public LilianaUntouchedByDeathGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast Zombie cards from your graveyard this turn";
    }

    public LilianaUntouchedByDeathGraveyardEffect(final LilianaUntouchedByDeathGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LilianaUntouchedByDeathGraveyardEffect copy() {
        return new LilianaUntouchedByDeathGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null
                    && card.hasSubtype(SubType.ZOMBIE, game)
                    && card.isOwnedBy(source.getControllerId())
                    && game.getState().getZone(objectId) == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }
}
