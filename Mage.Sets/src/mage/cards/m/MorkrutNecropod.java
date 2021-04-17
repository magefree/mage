
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class MorkrutNecropod extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another creature or land");

    static {       
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public MorkrutNecropod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.SLUG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Morkrut Necropod attacks or blocks, sacrifice another creature or land.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new SacrificeControllerEffect(filter, 1, ""), false));
    }

    private MorkrutNecropod(final MorkrutNecropod card) {
        super(card);
    }

    @Override
    public MorkrutNecropod copy() {
        return new MorkrutNecropod(this);
    }
}
