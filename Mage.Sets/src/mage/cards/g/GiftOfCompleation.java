package mage.cards.g;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiftOfCompleation extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.PHYREXIAN, "a Phyrexian you control");

    public GiftOfCompleation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When Gift of Compleation enters the battlefield, incubate 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(3)));

        // Whenever a Phyrexian you control dies, surveil 1.
        this.addAbility(new DiesCreatureTriggeredAbility(new SurveilEffect(1), false, filter));
    }

    private GiftOfCompleation(final GiftOfCompleation card) {
        super(card);
    }

    @Override
    public GiftOfCompleation copy() {
        return new GiftOfCompleation(this);
    }
}
