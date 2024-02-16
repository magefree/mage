package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.RoleType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AsinineAntics extends CardImpl {

    public AsinineAntics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // You may cast Asinine Antics as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}"));
        ability.addEffect(new AsinineAnticsEffect());
        this.addAbility(ability.setRuleAtTheTop(true));

        // For each creature your opponents control, create a Cursed Role token attached to that creature.
        this.getSpellAbility().addEffect(new AsinineAnticsEffect());
    }

    private AsinineAntics(final AsinineAntics card) {
        super(card);
    }

    @Override
    public AsinineAntics copy() {
        return new AsinineAntics(this);
    }
}

class AsinineAnticsEffect extends OneShotEffect {

    AsinineAnticsEffect() {
        super(Outcome.Benefit);
        staticText = "for each creature your opponents control, create a Cursed Role token attached to that creature";
    }

    private AsinineAnticsEffect(final AsinineAnticsEffect effect) {
        super(effect);
    }

    @Override
    public AsinineAnticsEffect copy() {
        return new AsinineAnticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, source.getControllerId(), source, game
        )) {
            RoleType.CURSED.createToken(permanent, game, source);
        }
        return true;
    }
}
