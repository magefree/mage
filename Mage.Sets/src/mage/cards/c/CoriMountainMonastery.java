package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoriMountainMonastery extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Plains or an Island");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate()
        ));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public CoriMountainMonastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a Plains or an Island.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {3}{R}, {T}: Exile the top card of your library. Until the end of your next turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn), new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CoriMountainMonastery(final CoriMountainMonastery card) {
        super(card);
    }

    @Override
    public CoriMountainMonastery copy() {
        return new CoriMountainMonastery(this);
    }
}
