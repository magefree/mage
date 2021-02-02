
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class AmrouSeekers extends CardImpl {

    private static final FilterCreaturePermanent notArtificatOrWhite = new FilterCreaturePermanent("except by artifact creatures and/or white creatures");

    static {
        notArtificatOrWhite.add(Predicates.not(
                Predicates.or(
                        CardType.ARTIFACT.getPredicate(),
                        new ColorPredicate(ObjectColor.WHITE)
                )
        ));
    }

    public AmrouSeekers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Amrou Seekers can't be blocked except by artifact creatures and/or white creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notArtificatOrWhite, Duration.WhileOnBattlefield)));

    }

    private AmrouSeekers(final AmrouSeekers card) {
        super(card);
    }

    @Override
    public AmrouSeekers copy() {
        return new AmrouSeekers(this);
    }
}
