
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class SweepEffect extends OneShotEffect {

    private final SubType sweepSubtype;

    public SweepEffect(SubType sweepSubtype) {
        super(Outcome.Benefit);
        this.sweepSubtype = sweepSubtype;
        this.staticText = "<i>Sweep</i> &mdash; Return any number of " + sweepSubtype + (sweepSubtype.getDescription().endsWith("s") ? "" : "s") + " you control to their owner's hand";
    }

    protected SweepEffect(final SweepEffect effect) {
        super(effect);
        this.sweepSubtype = effect.sweepSubtype;
    }

    @Override
    public SweepEffect copy() {
        return new SweepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanent filter = new FilterControlledLandPermanent("any number of " + sweepSubtype + "s you control");
            filter.add(sweepSubtype.getPredicate());
            Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
            if (controller.chooseTarget(outcome, target, source, game)) {
                game.getState().setValue(CardUtil.getCardZoneString("sweep", source.getSourceId(), game), target.getTargets().size());
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
