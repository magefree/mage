
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class VithianRenegades extends CardImpl {

    public VithianRenegades (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        
        // When Vithian Renegades enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private VithianRenegades(final VithianRenegades card) {
        super(card);
    }

    @Override
    public VithianRenegades copy() {
        return new VithianRenegades(this);
    }
}
