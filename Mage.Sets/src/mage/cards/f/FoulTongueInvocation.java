
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class FoulTongueInvocation extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public FoulTongueInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // As an additional cost to cast Foul-Tongue Invocation, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("as an additional cost to cast this spell, you may reveal a Dragon card from your hand"));

        // Target player sacrifices a creature. If you revealed a Dragon card or controlled a Dragon as you cast Foul-Tongue Invocation, you gain 4 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target player"));
        this.getSpellAbility().addEffect(new FoulTongueInvocationEffect());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, 1, filter)));
                }
            }
        }
    }

    public FoulTongueInvocation(final FoulTongueInvocation card) {
        super(card);
    }

    @Override
    public FoulTongueInvocation copy() {
        return new FoulTongueInvocation(this);
    }
}

class FoulTongueInvocationEffect extends OneShotEffect {

    public FoulTongueInvocationEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you revealed a Dragon card or controlled a Dragon as you cast {this}, you gain 4 life";
    }

    public FoulTongueInvocationEffect(final FoulTongueInvocationEffect effect) {
        super(effect);
    }

    @Override
    public FoulTongueInvocationEffect copy() {
        return new FoulTongueInvocationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class.getSimpleName());
            if (watcher != null && watcher.castWithConditionTrue(source.getId())) {
                controller.gainLife(4, game, source);
            }
            return true;
        }
        return false;
    }
}
