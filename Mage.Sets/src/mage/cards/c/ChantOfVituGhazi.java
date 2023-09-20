
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ChantOfVituGhazi extends CardImpl {

    public ChantOfVituGhazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{W}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Prevent all damage that would be dealt by creatures this turn. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new ChantOfVituGhaziPreventEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn, false));
    }

    private ChantOfVituGhazi(final ChantOfVituGhazi card) {
        super(card);
    }

    @Override
    public ChantOfVituGhazi copy() {
        return new ChantOfVituGhazi(this);
    }
}

class ChantOfVituGhaziPreventEffect extends PreventAllDamageByAllPermanentsEffect {

    public ChantOfVituGhaziPreventEffect(FilterCreaturePermanent filter, Duration duration, boolean onlyCombat) {
        super(filter, duration, onlyCombat);
        staticText = "Prevent all damage that would be dealt by creatures this turn. You gain life equal to the damage prevented this way";
    }

    private ChantOfVituGhaziPreventEffect(final ChantOfVituGhaziPreventEffect effect) {
        super(effect);
    }

    @Override
    public ChantOfVituGhaziPreventEffect copy() {
        return new ChantOfVituGhaziPreventEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(preventionData.getPreventedDamage(), game, source);
        }
        // damage amount is reduced or set to 0 so complete replacement of damage event is never neccessary
        return false;
    }

}
