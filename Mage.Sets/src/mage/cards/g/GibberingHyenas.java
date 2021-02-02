
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class GibberingHyenas extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public GibberingHyenas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HYENA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Gibbering Hyenas can't block black creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockCreaturesSourceEffect(filter)));
    }

    private GibberingHyenas(final GibberingHyenas card) {
        super(card);
    }

    @Override
    public GibberingHyenas copy() {
        return new GibberingHyenas(this);
    }
}
