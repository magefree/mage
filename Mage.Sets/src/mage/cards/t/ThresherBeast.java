
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class ThresherBeast extends CardImpl {

    public ThresherBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Thresher Beast becomes blocked, defending player sacrifices a land.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new ThresherBeastEffect(), false));
    }

    public ThresherBeast(final ThresherBeast card) {
        super(card);
    }

    @Override
    public ThresherBeast copy() {
        return new ThresherBeast(this);
    }
}

class ThresherBeastEffect extends OneShotEffect {

    public ThresherBeastEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player sacrifices a land";
    }

    public ThresherBeastEffect(final ThresherBeastEffect effect) {
        super(effect);
    }

    @Override
    public ThresherBeastEffect copy() {
        return new ThresherBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                Effect effect = new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "");
                effect.setTargetPointer(new FixedTarget(opponent.getId()));
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
