package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandBridge extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("an untapped permanent you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CommandBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When this land enters, sacrifice it unless you tap an untapped permanent you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new TapTargetCost(filter))
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private CommandBridge(final CommandBridge card) {
        super(card);
    }

    @Override
    public CommandBridge copy() {
        return new CommandBridge(this);
    }
}
