package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesOneOrMoreTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class GreatFierceBee extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GreatFierceBee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more other creatures die, scry 1.
        this.addAbility(new DiesOneOrMoreTriggeredAbility(new ScryEffect(1), filter, false));
    }

    private GreatFierceBee(final GreatFierceBee card) {
        super(card);
    }

    @Override
    public GreatFierceBee copy() {
        return new GreatFierceBee(this);
    }
}
