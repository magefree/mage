package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YueTheMoonSpirit extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature spell");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public YueTheMoonSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Waterbend {5}, {T}: You may cast a noncreature spell from your hand without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(new CastFromHandForFreeEffect(filter), new WaterbendCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private YueTheMoonSpirit(final YueTheMoonSpirit card) {
        super(card);
    }

    @Override
    public YueTheMoonSpirit copy() {
        return new YueTheMoonSpirit(this);
    }
}
