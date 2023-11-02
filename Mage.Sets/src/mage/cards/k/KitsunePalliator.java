
package mage.cards.k;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class KitsunePalliator extends CardImpl {

    public KitsunePalliator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Prevent the next 1 damage that would be dealt to each creature and each player this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KitsunePalliatorEffect(), new TapSourceCost()));
    }

    private KitsunePalliator(final KitsunePalliator card) {
        super(card);
    }

    @Override
    public KitsunePalliator copy() {
        return new KitsunePalliator(this);
    }
}

class KitsunePalliatorEffect extends OneShotEffect {

    public KitsunePalliatorEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent the next 1 damage that would be dealt to each creature and each player this turn";
    }

    private KitsunePalliatorEffect(final KitsunePalliatorEffect effect) {
        super(effect);
    }

    @Override
    public KitsunePalliatorEffect copy() {
        return new KitsunePalliatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PreventDamageToTargetEffect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, 1);

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                effect.setTargetPointer(new FixedTarget(player.getId()));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}
