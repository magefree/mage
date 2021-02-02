
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CastOnlyIfYouHaveCastAnotherSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class SkyshroudCondor extends CardImpl {

    public SkyshroudCondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cast Skyshroud Condor only if you've cast another spell this turn.
       this.addAbility(new SimpleStaticAbility(Zone.ALL, new CastOnlyIfYouHaveCastAnotherSpellEffect()));
    }

    private SkyshroudCondor(final SkyshroudCondor card) {
        super(card);
    }

    @Override
    public SkyshroudCondor copy() {
        return new SkyshroudCondor(this);
    }
}
