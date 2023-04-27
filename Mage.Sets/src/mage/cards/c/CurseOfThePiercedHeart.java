package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfThePiercedHeart extends CardImpl {

    public CurseOfThePiercedHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, Curse of the Pierced Heart deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CurseOfThePiercedHeartEffect(), TargetController.ENCHANTED, false
        ));
    }

    private CurseOfThePiercedHeart(final CurseOfThePiercedHeart card) {
        super(card);
    }

    @Override
    public CurseOfThePiercedHeart copy() {
        return new CurseOfThePiercedHeart(this);
    }
}

class CurseOfThePiercedHeartEffect extends OneShotEffect {

    CurseOfThePiercedHeartEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to that player or a planeswalker that player controls";
    }

    private CurseOfThePiercedHeartEffect(final CurseOfThePiercedHeartEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfThePiercedHeartEffect copy() {
        return new CurseOfThePiercedHeartEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER, opponent.getId(), source, game) < 1
                || !controller.chooseUse(Outcome.Damage, "Redirect to a planeswalker controlled by " + opponent.getLogName() + "?", source, game)) {
            return opponent.damage(1, source.getSourceId(), source, game) > 0;
        }
        FilterPermanent filter = new FilterPlaneswalkerPermanent("a planeswalker controlled by " + opponent.getLogName());
        filter.add(new ControllerIdPredicate(opponent.getId()));
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        controller.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            return permanent.damage(1, source.getSourceId(), source, game, false, true) > 0;
        }
        return opponent.damage(1, source.getSourceId(), source, game) > 0;
    }
}
