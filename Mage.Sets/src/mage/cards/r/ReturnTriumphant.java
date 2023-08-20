package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ReturnTriumphant extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public ReturnTriumphant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Return target creature card with mana value 3 or less from your graveyard to the battlefield. Create a Young Hero Role token attached to it.
        this.getSpellAbility().addEffect(new ReturnTriumphantAbility());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private ReturnTriumphant(final ReturnTriumphant card) {
        super(card);
    }

    @Override
    public ReturnTriumphant copy() {
        return new ReturnTriumphant(this);
    }
}

class ReturnTriumphantAbility extends OneShotEffect {

    ReturnTriumphantAbility() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return target creature card with mana value 3 or less from your graveyard "
                + "to the battlefield. Create a Young Hero Role token attached to it";
    }

    private ReturnTriumphantAbility(final ReturnTriumphantAbility effect) {
        super(effect);
    }

    @Override
    public ReturnTriumphantAbility copy() {
        return new ReturnTriumphantAbility(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }

        return new CreateRoleAttachedTargetEffect(RoleType.YOUNG_HERO)
                .setTargetPointer(new FixedTarget(permanent, game))
                .apply(game, source);
    }

}