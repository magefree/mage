
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class DefiantOgre extends CardImpl {

    public DefiantOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Defiant Ogre enters the battlefield, choose one -
        // * Put a +1/+1 counter on Defiant Ogre.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
        // * Destroy target artifact.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetArtifactPermanent());
        ability.addMode(mode);
        this.addAbility(ability);           
    }

    private DefiantOgre(final DefiantOgre card) {
        super(card);
    }

    @Override
    public DefiantOgre copy() {
        return new DefiantOgre(this);
    }
}
