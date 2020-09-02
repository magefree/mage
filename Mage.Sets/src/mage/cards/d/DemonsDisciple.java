package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author TheElk801
 */
public final class DemonsDisciple extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("a creature or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public DemonsDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Demon's Disciple enters the battlefield, each player sacrifices a creature or planeswalker.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(1, filter)));
    }

    private DemonsDisciple(final DemonsDisciple card) {
        super(card);
    }

    @Override
    public DemonsDisciple copy() {
        return new DemonsDisciple(this);
    }
}
