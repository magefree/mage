
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.constants.ZoneDetail;
import mage.game.Game;
import mage.players.Player;

/**
 * @author nantuko, North
 */
public class CounterTargetWithReplacementEffect extends OneShotEffect {

    private Zone targetZone;
    private ZoneDetail zoneDetail;

    public CounterTargetWithReplacementEffect(Zone targetZone) {
        this(targetZone, ZoneDetail.NONE);
    }

    /**
     *
     * @param targetZone
     * @param zoneDetail use to specify when moving card to library <ul><li>true
     * = put on top</li><li>false = put on bottom</li></ul>
     */
    public CounterTargetWithReplacementEffect(Zone targetZone, ZoneDetail zoneDetail) {
        super(Outcome.Detriment);
        this.targetZone = targetZone;
        this.zoneDetail = zoneDetail;
    }

    public CounterTargetWithReplacementEffect(final CounterTargetWithReplacementEffect effect) {
        super(effect);
        this.targetZone = effect.targetZone;
        this.zoneDetail = effect.zoneDetail;
    }

    @Override
    public CounterTargetWithReplacementEffect copy() {
        return new CounterTargetWithReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return game.getStack().counter(targetPointer.getFirst(game, source), source, game, targetZone, false, zoneDetail);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("Counter target ");
        sb.append(mode.getTargets().get(0).getTargetName());
        sb.append(". If that spell is countered this way, ");
        if (targetZone == Zone.EXILED) {
            sb.append("exile it instead of putting it into its owner's graveyard");
        }
        if (targetZone == Zone.HAND) {
            sb.append("put it into its owner's hand instead of into that player's graveyard");
        }
        if (targetZone == Zone.LIBRARY) {
            sb.append("put it on ");
            switch (zoneDetail) {
                case BOTTOM:
                    sb.append("the bottom");
                    break;
                case TOP:
                    sb.append("top");
                    break;
                case CHOOSE:
                    sb.append("top or bottom");
                    break;
                case NONE:
                    sb.append("<not allowed value>");
                    break;
            }
            sb.append(" of its owner's library instead of into that player's graveyard");
        }

        return sb.toString();
    }
}
