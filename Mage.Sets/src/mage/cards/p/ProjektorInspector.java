package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ProjektorInspector extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent(SubType.DETECTIVE, "another Detective");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.DETECTIVE, "a Detective you control");

    static {
        filter1.add(AnotherPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public ProjektorInspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Projektor Inspector or another Detective enters the battlefield under your control and whenever a Detective you control is turned face up, you may draw a card. If you do, discard a card.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new DrawDiscardControllerEffect(true),
                false,
                "Whenever {this} or another Detective enters the battlefield under your control and "
                        + "whenever a Detective you control is turned face up, ",
                new EntersBattlefieldTriggeredAbility(null),
                new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, null, filter1, false),
                new TurnedFaceUpAllTriggeredAbility(null, filter2)
        ));
    }

    private ProjektorInspector(final ProjektorInspector card) {
        super(card);
    }

    @Override
    public ProjektorInspector copy() {
        return new ProjektorInspector(this);
    }
}
