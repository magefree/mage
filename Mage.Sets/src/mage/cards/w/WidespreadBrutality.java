package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WidespreadBrutality extends CardImpl {

    public WidespreadBrutality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}{R}");

        // Amass 2, then the Army you amassed deals damage equal to its power to each non-Army creature.
        this.getSpellAbility().addEffect(new WidespreadBrutalityEffect());
    }

    private WidespreadBrutality(final WidespreadBrutality card) {
        super(card);
    }

    @Override
    public WidespreadBrutality copy() {
        return new WidespreadBrutality(this);
    }
}

class WidespreadBrutalityEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.ARMY.getPredicate()));
    }

    WidespreadBrutalityEffect() {
        super(Outcome.Benefit);
        staticText = "amass Zombies 2, then the Army you amassed deals damage equal to its power " +
                "to each non-Army creature. <i>(To amass 2, put two +1/+1 counters on an Army you control. It's also " +
                "a Zombie. If you don't control one, create a 0/0 black Zombie Army creature token first.)</i>";
    }

    private WidespreadBrutalityEffect(final WidespreadBrutalityEffect effect) {
        super(effect);
    }

    @Override
    public WidespreadBrutalityEffect copy() {
        return new WidespreadBrutalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent amassedArmy = AmassEffect.doAmass(2, SubType.ZOMBIE, game, source);
        if (amassedArmy == null) {
            return false;
        }
        game.getState().processAction(game);
        int power = amassedArmy.getPower().getValue();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            permanent.damage(power, amassedArmy.getId(), source, game);
        }
        return true;
    }
}