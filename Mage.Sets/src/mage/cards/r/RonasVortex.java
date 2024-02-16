package mage.cards.r;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RonasVortex extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public RonasVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Kicker {2}{B}
        this.addAbility(new KickerAbility("{2}{B}"));

        // Return target creature or planeswalker you don't control to its owner's hand. If this spell was kicked, put that permanent on the bottom of its owner's library instaed.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new PutOnLibraryTargetEffect(false), new ReturnToHandTargetEffect(),
                KickedCondition.ONCE, "return target creature or planeswalker you don't control to its owner's " +
                "hand. If this spell was kicked, put that permanent on the bottom of its owner's library instead"
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private RonasVortex(final RonasVortex card) {
        super(card);
    }

    @Override
    public RonasVortex copy() {
        return new RonasVortex(this);
    }
}
