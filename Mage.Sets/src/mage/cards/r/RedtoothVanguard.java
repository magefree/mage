package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RedtoothVanguard extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("an enchantment");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RedtoothVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever an enchantment enters the battlefield under your control, you may pay 2. If you do, return Redtooth Vanguard from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToHandEffect(),
                        new GenericManaCost(2)
                ),
                filter, false, SetTargetPointer.NONE, null, true
        ));
    }

    private RedtoothVanguard(final RedtoothVanguard card) {
        super(card);
    }

    @Override
    public RedtoothVanguard copy() {
        return new RedtoothVanguard(this);
    }
}
