
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KorCastigator extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("Eldrazi Scions");

    static {
        FILTER.add(SubType.ELDRAZI.getPredicate());
        FILTER.add(SubType.SCION.getPredicate());
    }

    public KorCastigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kor Castigator can't be blocked by Eldrazi Scions.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(FILTER, Duration.WhileOnBattlefield)));
    }

    private KorCastigator(final KorCastigator card) {
        super(card);
    }

    @Override
    public KorCastigator copy() {
        return new KorCastigator(this);
    }
}
