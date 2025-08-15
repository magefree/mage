package mage.cards.e;

import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthbendingLesson extends CardImpl {

    public EarthbendingLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        this.subtype.add(SubType.LESSON);

        // Earthbend 4.
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent());
    }

    private EarthbendingLesson(final EarthbendingLesson card) {
        super(card);
    }

    @Override
    public EarthbendingLesson copy() {
        return new EarthbendingLesson(this);
    }
}
