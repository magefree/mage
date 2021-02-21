
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class Timbermare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Timbermare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Echo {5}{G}
        this.addAbility(new EchoAbility("{5}{G}"));
        // When Timbermare enters the battlefield, tap all other creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapAllEffect(filter)));
    }

    private Timbermare(final Timbermare card) {
        super(card);
    }

    @Override
    public Timbermare copy() {
        return new Timbermare(this);
    }
}
