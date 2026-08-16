package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class BuckyBarnesEagerAlly extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment, Hero, or Soldier card");

    static {
        filter.add(Predicates.or(
            SubType.EQUIPMENT.getPredicate(),
            SubType.HERO.getPredicate(),
            SubType.SOLDIER.getPredicate()
        ));
    }

    public BuckyBarnesEagerAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Bucky Barnes dies, look at the top four cards of your library. You may reveal an Equipment, Hero, or Soldier card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new DiesSourceTriggeredAbility(new LookLibraryAndPickControllerEffect(
            4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ));
        this.addAbility(ability);
    }

    private BuckyBarnesEagerAlly(final BuckyBarnesEagerAlly card) {
        super(card);
    }

    @Override
    public BuckyBarnesEagerAlly copy() {
        return new BuckyBarnesEagerAlly(this);
    }
}
