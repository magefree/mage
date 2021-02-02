
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class ArchonOfTheTriumvirate extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents your opponents control");
 
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }
    
    public ArchonOfTheTriumvirate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{U}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Archon of the Triumvirate attacks, detain up to two target nonland permanents your opponents control.
        // (Until your next turn, those permanents can't attack or block and their activated abilities can't be activated.)
        Ability ability = new AttacksTriggeredAbility(new DetainTargetEffect(), false);
        ability.addTarget(new TargetNonlandPermanent(0,2,filter, false));
        this.addAbility(ability);
    }

    private ArchonOfTheTriumvirate(final ArchonOfTheTriumvirate card) {
        super(card);
    }

    @Override
    public ArchonOfTheTriumvirate copy() {
        return new ArchonOfTheTriumvirate(this);
    }
}
