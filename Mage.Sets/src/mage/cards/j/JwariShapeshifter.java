
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class JwariShapeshifter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Ally creature");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(AnotherPredicate.instance); // needed because during enters_the_battlefield event the creature is already targetable although it shouldn't
    }

    public JwariShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Jwari Shapeshifter enter the battlefield as a copy of any Ally creature on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(filter), true));
    }

    private JwariShapeshifter(final JwariShapeshifter card) {
        super(card);
    }

    @Override
    public JwariShapeshifter copy() {
        return new JwariShapeshifter(this);
    }
}
