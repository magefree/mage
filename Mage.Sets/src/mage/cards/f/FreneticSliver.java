
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author anonymous
 */
public final class FreneticSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public FreneticSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it."
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new FreneticSliverEffect(), new ManaCostsImpl("{0}"), SourceOnBattlefieldCondition.instance, "{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it.");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield, filter, "All Slivers have \"{0}: If this permanent is on the battlefield, flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it.\"")));
    }

    public FreneticSliver(final FreneticSliver card) {
        super(card);
    }

    @Override
    public FreneticSliver copy() {
        return new FreneticSliver(this);
    }
}

class FreneticSliverEffect extends OneShotEffect {

    public FreneticSliverEffect() {
        super(Outcome.Neutral);
        staticText = "Flip a coin. If you win the flip, exile this permanent and return it to the battlefield under its owner's control at the beginning of the next end step. If you lose the flip, sacrifice it";
    }

    public FreneticSliverEffect(final FreneticSliverEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(game)) {
                return new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(true).apply(game, source);
            } else {
                Permanent perm = game.getPermanent(source.getSourceId());
                if (perm != null) {
                    perm.sacrifice(source.getSourceId(), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public FreneticSliverEffect copy() {
        return new FreneticSliverEffect(this);
    }
}
