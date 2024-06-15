package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author xenohedron
 */
public class GainControlAllUntapGainHasteEffect extends OneShotEffect {

    private final FilterPermanent filter;

    /**
     * Gain control of all [filter] until end of turn. Untap them. They gain haste until end of turn.
     */
    public GainControlAllUntapGainHasteEffect(FilterPermanent filter) {
        super(Outcome.GainControl);
        this.filter = filter;
        makeText("them");
    }

    protected GainControlAllUntapGainHasteEffect(final GainControlAllUntapGainHasteEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public GainControlAllUntapGainHasteEffect copy() {
        return new GainControlAllUntapGainHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> affectedObjects = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        if (affectedObjects.isEmpty()) {
            return false;
        }
        FilterPermanent affectedFilter = new FilterPermanent();
        affectedFilter.add(new PermanentReferenceInCollectionPredicate(affectedObjects, game));
        new GainControlAllEffect(Duration.EndOfTurn, affectedFilter).apply(game, source);
        game.processAction();
        new UntapAllEffect(affectedFilter).apply(game, source);
        game.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, affectedFilter), source);
        return true;
    }

    private void makeText(String those) {
        this.staticText = "gain control of all " + filter.getMessage() + " until end of turn. Untap "
                + those + ". They gain haste until end of turn";
    }

    public GainControlAllUntapGainHasteEffect withTextOptions(String those) {
        makeText(those);
        return this;
    }

}
