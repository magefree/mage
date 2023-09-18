package mage.cards.o;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OffspringsRevenge extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("red, white, or black creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLACK)
        ));
    }

    public OffspringsRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}{B}");

        // At the beginning of combat on your turn, exile target red, white, or black creature card from your graveyard. Create a token that's a copy of that card, except it's 1/1. It gains haste until your next turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new OffspringsRevengeEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private OffspringsRevenge(final OffspringsRevenge card) {
        super(card);
    }

    @Override
    public OffspringsRevenge copy() {
        return new OffspringsRevenge(this);
    }
}

class OffspringsRevengeEffect extends OneShotEffect {

    OffspringsRevengeEffect() {
        super(Outcome.Benefit);
        staticText = "exile target red, white, or black creature card from your graveyard. " +
                "Create a token that's a copy of that card, except it's 1/1. It gains haste until your next turn.";
    }

    private OffspringsRevengeEffect(final OffspringsRevengeEffect effect) {
        super(effect);
    }

    @Override
    public OffspringsRevengeEffect copy() {
        return new OffspringsRevengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 1, false,
                false, null, 1, 1, false
        );
        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
        player.moveCards(card, Zone.EXILED, source, game);
        effect.apply(game, source);
        effect.getAddedPermanents().stream().forEach(permanent -> {
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.UntilYourNextTurn
            );
            continuousEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(continuousEffect, source);
        });
        return true;
    }
}
