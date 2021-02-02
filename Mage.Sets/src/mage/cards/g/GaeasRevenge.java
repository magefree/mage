
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GaeasRevenge extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("nongreen spells or abilities from nongreen sources");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }

    public GaeasRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()));
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(filter, Duration.WhileOnBattlefield)));

    }

    private GaeasRevenge(final GaeasRevenge card) {
        super(card);
    }

    @Override
    public GaeasRevenge copy() {
        return new GaeasRevenge(this);
    }

}
