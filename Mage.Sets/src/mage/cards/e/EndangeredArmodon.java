
package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class EndangeredArmodon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with toughness 2 or less");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public EndangeredArmodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When you control a creature with toughness 2 or less, sacrifice Endangered Armodon.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                filter,
                new SacrificeSourceEffect()));
    }

    private EndangeredArmodon(final EndangeredArmodon card) {
        super(card);
    }

    @Override
    public EndangeredArmodon copy() {
        return new EndangeredArmodon(this);
    }
}
