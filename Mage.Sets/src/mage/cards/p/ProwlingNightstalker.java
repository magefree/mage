
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class ProwlingNightstalker extends CardImpl {

    private static final FilterCreaturePermanent notBlackCreatures = new FilterCreaturePermanent("except by black creatures");

    static {
        notBlackCreatures.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public ProwlingNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowling Nightstalker can't be blocked except by black creatures.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(notBlackCreatures, Duration.WhileOnBattlefield)));
    }

    private ProwlingNightstalker(final ProwlingNightstalker card) {
        super(card);
    }

    @Override
    public ProwlingNightstalker copy() {
        return new ProwlingNightstalker(this);
    }
}
