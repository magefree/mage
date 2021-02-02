
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MerfolkWizardToken;

/**
 *
 * @author fireshoes
 */
public final class StonybrookSchoolmaster extends CardImpl {

    public StonybrookSchoolmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Stonybrook Schoolmaster becomes tapped, you may create a 1/1 blue Merfolk Wizard creature token.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new CreateTokenEffect(new MerfolkWizardToken()), true));
    }

    private StonybrookSchoolmaster(final StonybrookSchoolmaster card) {
        super(card);
    }

    @Override
    public StonybrookSchoolmaster copy() {
        return new StonybrookSchoolmaster(this);
    }
}
