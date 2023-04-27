
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author MarcoMarin
 */
public final class GoblinKites extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("controlled creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GoblinKites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // {R}: Target creature you control with toughness 2 or less gains flying until end of turn. Flip a coin at the beginning of the next end step. If you lose the flip, sacrifice that creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new GainAbilityTargetEffect(new BeginningOfEndStepTriggeredAbility(new GoblinKitesEffect(), TargetController.NEXT, false), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private GoblinKites(final GoblinKites card) {
        super(card);
    }

    @Override
    public GoblinKites copy() {
        return new GoblinKites(this);
    }
}
class GoblinKitesEffect extends OneShotEffect {

    public GoblinKitesEffect() {
        super(Outcome.Damage);
        staticText = "flip a coin. If you lose the flip, sacrifice {this}";
    }

    public GoblinKitesEffect(GoblinKitesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(source, game, true)) {
                return true;
            } else {
                new SacrificeSourceEffect().apply(game, source);
                return true;
                }
            }
        return false;
    }

    @Override
    public GoblinKitesEffect copy() {
        return new GoblinKitesEffect(this);
    }
}
