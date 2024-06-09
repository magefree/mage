package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TanglespanLookout extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.AURA, "an Aura");

    public TanglespanLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SATYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever an Aura enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));
    }

    private TanglespanLookout(final TanglespanLookout card) {
        super(card);
    }

    @Override
    public TanglespanLookout copy() {
        return new TanglespanLookout(this);
    }
}
