
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33, BetaSteward (GraveDigger)
 */
 
public final class Scrivener extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant card from your graveyard");
    
    static{
        filter.add(CardType.INSTANT.getPredicate());
    }       
    
    public Scrivener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Scrivener enters the battlefield, you may return target instant card from your graveyard to your hand.
       Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
       ability.addTarget(new TargetCardInYourGraveyard(filter));
       this.addAbility(ability);
        
    }

    private Scrivener(final Scrivener card) {
        super(card);
    }

    @Override
    public Scrivener copy() {
        return new Scrivener(this);
    }
}
