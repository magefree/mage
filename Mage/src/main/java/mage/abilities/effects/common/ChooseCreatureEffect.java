package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 * To be used with AsEntersBattlefieldAbility (otherwise Zone Change Counter will be wrong)
 *
 * @author weirddan455
 */
public class ChooseCreatureEffect extends OneShotEffect {

    private static final FilterPermanent defaultFilter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        defaultFilter.add(AnotherPredicate.instance);
    }

    private final FilterPermanent filter;

    public ChooseCreatureEffect() {
        this(defaultFilter);
    }

    public ChooseCreatureEffect(FilterPermanent filter) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = "choose " + filter.getMessage();
    }

    private ChooseCreatureEffect(final ChooseCreatureEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ChooseCreatureEffect copy() {
        return new ChooseCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(1, 1, filter, true);
        controller.chooseTarget(outcome, target, source, game);
        Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
        if (chosenCreature == null) {
            return false;
        }
        game.getState().setValue(
                CardUtil.getObjectZoneString("chosenCreature", sourcePermanent.getId(), game, sourcePermanent.getZoneChangeCounter(game) + 1, false),
                new MageObjectReference(chosenCreature, game)
        );
        sourcePermanent.addInfo("chosen creature", CardUtil.addToolTipMarkTags("Chosen Creature " + chosenCreature.getIdName()), game);
        return true;
    }
}
