
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
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
public final class NovablastWurm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CardType.CREATURE.getPredicate());
    }

    public NovablastWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}{W}{W}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever Novablast Wurm attacks, destroy all other creatures.
        this.addAbility(new AttacksTriggeredAbility(new DestroyAllEffect(filter), false));
    }

    private NovablastWurm(final NovablastWurm card) {
        super(card);
    }

    @Override
    public NovablastWurm copy() {
        return new NovablastWurm(this);
    }
}
