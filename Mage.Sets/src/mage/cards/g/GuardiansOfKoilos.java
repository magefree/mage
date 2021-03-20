package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Rystan
 */
public final class GuardiansOfKoilos extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another historic permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(HistoricPredicate.instance);
    }

    public GuardiansOfKoilos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Guardians of Koilos enters the battlefield, you may return another target historic permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter)
                .setText("you may return another target historic permanent you control to its owner's hand. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"), true));
    }

    private GuardiansOfKoilos(final GuardiansOfKoilos card) {
        super(card);
    }

    @Override
    public GuardiansOfKoilos copy() {
        return new GuardiansOfKoilos(this);
    }
}
