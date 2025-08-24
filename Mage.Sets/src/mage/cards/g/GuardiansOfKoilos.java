package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

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
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("you may return another target historic permanent you control to its owner's hand. <i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GuardiansOfKoilos(final GuardiansOfKoilos card) {
        super(card);
    }

    @Override
    public GuardiansOfKoilos copy() {
        return new GuardiansOfKoilos(this);
    }
}
