package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TwinflameTravelers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELEMENTAL, "another Elemental you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TwinflameTravelers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a triggered ability of another Elemental you control triggers, it triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter)));
    }

    private TwinflameTravelers(final TwinflameTravelers card) {
        super(card);
    }

    @Override
    public TwinflameTravelers copy() {
        return new TwinflameTravelers(this);
    }
}
