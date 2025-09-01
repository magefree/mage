package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlAllUntapGainHasteEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MutinousMassacre extends CardImpl {

    public MutinousMassacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}{R}{R}");

        // Choose odd or even. Destroy each creature with mana value of the chosen quality. Then gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new MutinousMassacreEffect());
        this.getSpellAbility().addEffect(new GainControlAllUntapGainHasteEffect(StaticFilters.FILTER_PERMANENT_CREATURES).concatBy("Then"));
    }

    private MutinousMassacre(final MutinousMassacre card) {
        super(card);
    }

    @Override
    public MutinousMassacre copy() {
        return new MutinousMassacre(this);
    }
}

class MutinousMassacreEffect extends OneShotEffect {

    private static final FilterPermanent evenFilter = new FilterCreaturePermanent();
    private static final FilterPermanent oddFilter = new FilterCreaturePermanent();

    static {
        evenFilter.add(ManaValueParityPredicate.EVEN);
        oddFilter.add(ManaValueParityPredicate.ODD);
    }

    MutinousMassacreEffect() {
        super(Outcome.Benefit);
        staticText = "choose odd or even. Destroy each creature with mana value of the chosen quality";
    }

    private MutinousMassacreEffect(final MutinousMassacreEffect effect) {
        super(effect);
    }

    @Override
    public MutinousMassacreEffect copy() {
        return new MutinousMassacreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = player.chooseUse(
                outcome, "Odd or even?", null,
                "Odd", "Even", source, game
        ) ? oddFilter : evenFilter;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
