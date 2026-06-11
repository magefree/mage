package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RickJonesDestinedSidekick extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Hero or enchantment card");

    static {
        filter.add(Predicates.or(
            SubType.HERO.getPredicate(),
            CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public RickJonesDestinedSidekick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {3}{T}: Mill four cards. You may put a Hero or enchantment card from among those cards into your hand.
        Ability ability = new SimpleActivatedAbility(
            new MillThenPutInHandEffect(4, filter)
                 .withTextOptions("those cards"),
            new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RickJonesDestinedSidekick(final RickJonesDestinedSidekick card) {
        super(card);
    }

    @Override
    public RickJonesDestinedSidekick copy() {
        return new RickJonesDestinedSidekick(this);
    }
}
