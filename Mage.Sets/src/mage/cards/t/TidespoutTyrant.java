
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class TidespoutTyrant extends CardImpl {

    public TidespoutTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell, return target permanent to its owner's hand.
        Ability ability = new SpellCastControllerTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetPermanent());        
        this.addAbility(ability);
    }

    private TidespoutTyrant(final TidespoutTyrant card) {
        super(card);
    }

    @Override
    public TidespoutTyrant copy() {
        return new TidespoutTyrant(this);
    }
}
