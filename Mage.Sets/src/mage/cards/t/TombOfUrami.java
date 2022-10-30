package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeAllCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.UramiToken;

/**
 *
 * @author Plopman
 */
public final class TombOfUrami extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Ogre");

    static {
        filter.add(SubType.OGRE.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public TombOfUrami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.addSuperType(SuperType.LEGENDARY);

        // {tap}: Add {B}. Tomb of Urami deals 1 damage to you if you don't control an Ogre.
        Ability ability = new BlackManaAbility();
        ability.addEffect(new ConditionalOneShotEffect(
                new DamageControllerEffect(1),
                condition,
                "{this} deals 1 damage to you if you don't control an Ogre"
        ));
        this.addAbility(ability);

        // {2}{B}{B}, {tap}, Sacrifice all lands you control: Create a legendary 5/5 black Demon Spirit creature token with flying named Urami.
        Ability ability2 = new SimpleActivatedAbility(new CreateTokenEffect(new UramiToken()), new ManaCostsImpl<>("{2}{B}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeAllCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
        this.addAbility(ability2);
    }

    private TombOfUrami(final TombOfUrami card) {
        super(card);
    }

    @Override
    public TombOfUrami copy() {
        return new TombOfUrami(this);
    }
}
