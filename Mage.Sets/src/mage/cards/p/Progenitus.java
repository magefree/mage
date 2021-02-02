
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author North
 */
public final class Progenitus extends CardImpl {

    public Progenitus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        //     2/1/2009: "Protection from everything" means the following: Progenitus can't be blocked,
        //               Progenitus can't be enchanted or equipped, Progenitus can't be the target of
        //               spells or abilities, and all damage that would be dealt to Progenitus is prevented.
        //     2/1/2009: Progenitus can still be affected by effects that don't target it or deal damage
        //               to it (such as Day of Judgment).
        
        // Protection from everything
        this.addAbility(new ProgenitusProtectionAbility());
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    private Progenitus(final Progenitus card) {
        super(card);
    }

    @Override
    public Progenitus copy() {
        return new Progenitus(this);
    }
}

class ProgenitusProtectionAbility extends ProtectionAbility {

    public ProgenitusProtectionAbility() {
        super(new FilterCard("everything"));
    }

    public ProgenitusProtectionAbility(final ProgenitusProtectionAbility ability) {
        super(ability);
    }

    @Override
    public ProgenitusProtectionAbility copy() {
        return new ProgenitusProtectionAbility(this);
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        return false;
    }
}
