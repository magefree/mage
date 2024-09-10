package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetSpellOrPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PressTheEnemy extends CardImpl {

    private static final FilterSpellOrPermanent filter
            = new FilterSpellOrPermanent("spell or nonland permanent an opponent controls");

    static {
        filter.getSpellFilter().add(TargetController.OPPONENT.getControllerPredicate());
        filter.getPermanentFilter().add(TargetController.OPPONENT.getControllerPredicate());
        filter.getPermanentFilter().add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public PressTheEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Return target spell or nonland permanent an opponent controls to its owner's hand. You may cast an instant or sorcery spell with equal or lesser mana value from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new PressTheEnemyEffect());
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));
    }

    private PressTheEnemy(final PressTheEnemy card) {
        super(card);
    }

    @Override
    public PressTheEnemy copy() {
        return new PressTheEnemy(this);
    }
}

class PressTheEnemyEffect extends OneShotEffect {

    PressTheEnemyEffect() {
        super(Outcome.Benefit);
        staticText = "return target spell or nonland permanent an opponent controls to its owner's hand. You may cast " +
                "an instant or sorcery spell with equal or lesser mana value from your hand without paying its mana cost";
    }

    private PressTheEnemyEffect(final PressTheEnemyEffect effect) {
        super(effect);
    }

    @Override
    public PressTheEnemyEffect copy() {
        return new PressTheEnemyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int mv;
        UUID targetId = getTargetPointer().getFirst(game, source);
        Spell spell = game.getSpell(targetId);
        if (spell == null) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            mv = permanent.getManaValue();
            player.moveCards(permanent, Zone.HAND, source, game);
        } else {
            mv = spell.getManaValue();
            player.moveCards(spell, Zone.HAND, source, game);
        }
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, mv + 1));
        CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(player.getHand()), filter);
        return true;
    }
}
