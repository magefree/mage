package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnafenzaUnyieldingLineage extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public AnafenzaUnyieldingLineage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever another nontoken creature you control dies, Anafenza endures 2.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new EndureSourceEffect(2, "{this}"), false, filter
        ));
    }

    private AnafenzaUnyieldingLineage(final AnafenzaUnyieldingLineage card) {
        super(card);
    }

    @Override
    public AnafenzaUnyieldingLineage copy() {
        return new AnafenzaUnyieldingLineage(this);
    }
}
