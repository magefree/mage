package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KishlaVillage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an Island or a Swamp");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public KishlaVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control an Island or a Swamp.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {3}{G}, {T}: Surveil 2.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(2), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KishlaVillage(final KishlaVillage card) {
        super(card);
    }

    @Override
    public KishlaVillage copy() {
        return new KishlaVillage(this);
    }
}
