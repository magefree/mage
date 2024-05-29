package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SinkIntoStupor extends ModalDoubleFacedCard {

    private static final FilterSpellOrPermanent filter
            = new FilterSpellOrPermanent("spell or nonland permanent an opponent controls");

    static {
        filter.getSpellFilter().add(TargetController.OPPONENT.getControllerPredicate());
        filter.getPermanentFilter().add(TargetController.OPPONENT.getControllerPredicate());
        filter.getPermanentFilter().add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public SinkIntoStupor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{U}{U}",
                "Soporific Springs", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Sink into Stupor
        // Instant

        // Return target spell or nonland permanent an opponent controls to its owner's hand.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));

        // 2.
        // Soporific Springs
        // Land

        // As Soporific Springs enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private SinkIntoStupor(final SinkIntoStupor card) {
        super(card);
    }

    @Override
    public SinkIntoStupor copy() {
        return new SinkIntoStupor(this);
    }
}
