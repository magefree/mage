
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class AscendantEvincar extends CardImpl {

    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("black creatures");
    private static final FilterCreaturePermanent filterNonBlack = new FilterCreaturePermanent("Nonblack creatures");

    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterNonBlack.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public AscendantEvincar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterBlack, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield, filterNonBlack, false)));
    }

    private AscendantEvincar(final AscendantEvincar card) {
        super(card);
    }

    @Override
    public AscendantEvincar copy() {
        return new AscendantEvincar(this);
    }
}
