package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpeditionSupplier extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Human");

    public ExpeditionSupplier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Expedition Supplier or another Human enters the battlefield under your control, conjure a card named Utility Knife onto the battlefield. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new ConjureCardEffect(
                "Utility Knife", Zone.BATTLEFIELD, 1
        ), filter, false, true).setTriggersOnceEachTurn(true));
    }

    private ExpeditionSupplier(final ExpeditionSupplier card) {
        super(card);
    }

    @Override
    public ExpeditionSupplier copy() {
        return new ExpeditionSupplier(this);
    }
}
