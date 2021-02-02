
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class RavenousBaboons extends CardImpl {

    public RavenousBaboons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.MONKEY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ravenous Baboons enters the battlefield, destroy target nonbasic land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetNonBasicLandPermanent());        
        this.addAbility(ability);
    }

    private RavenousBaboons(final RavenousBaboons card) {
        super(card);
    }

    @Override
    public RavenousBaboons copy() {
        return new RavenousBaboons(this);
    }
}
