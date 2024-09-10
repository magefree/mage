package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreakTheIce extends CardImpl {

    private static final FilterPermanent filter
            = new FilterLandPermanent("land that is snow or could produce {C}");

    static {
        filter.add(BreakTheIcePredicate.instance);
    }

    public BreakTheIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Destroy target land that is snow or could produce {C}.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Overload {4}{B}{B}
        this.addAbility(new OverloadAbility(this, new DestroyAllEffect(filter), new ManaCostsImpl<>("{4}{B}{B}")));
    }

    private BreakTheIce(final BreakTheIce card) {
        super(card);
    }

    @Override
    public BreakTheIce copy() {
        return new BreakTheIce(this);
    }
}

enum BreakTheIcePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isSnow(game)
                || input
                .getAbilities()
                .getActivatedManaAbilities(Zone.BATTLEFIELD)
                .stream()
                .anyMatch(ability -> ability.getProducableManaTypes(game).contains(ManaType.COLORLESS));
    }
}
