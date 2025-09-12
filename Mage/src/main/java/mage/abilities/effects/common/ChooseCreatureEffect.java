package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 * To be used with AsEntersBattlefieldAbility with useOffset=false (otherwise Zone Change Counter will be wrong)
 *
 * @author weirddan455
 */
public class ChooseCreatureEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final boolean useOffset;

    public ChooseCreatureEffect() {
        this(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
    }

    public ChooseCreatureEffect(FilterPermanent filter, boolean useOffset) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = "choose " + CardUtil.addArticle(filter.getMessage());
        this.useOffset = useOffset;
    }

    private ChooseCreatureEffect(final ChooseCreatureEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.useOffset = effect.useOffset;
    }

    @Override
    public ChooseCreatureEffect copy() {
        return new ChooseCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentEntering(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = game.getPermanent(source.getSourceId());
        }
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(1, 1, filter, true);
        controller.chooseTarget(outcome, target, source, game);
        Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
        if (chosenCreature == null) {
            return false;
        }
        game.getState().setValue(
                CardUtil.getObjectZoneString(
                        "chosenCreature", sourcePermanent.getId(), game,
                        sourcePermanent.getZoneChangeCounter(game) + (useOffset ? 1 : 0), false
                ),
                new MageObjectReference(chosenCreature, game)
        );
        sourcePermanent.addInfo("chosen creature", CardUtil.addToolTipMarkTags("Chosen Creature " + chosenCreature.getIdName()), game);
        return true;
    }
}
