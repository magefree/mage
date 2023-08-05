
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Plopman
 */
public final class ColdsteelHeart extends CardImpl {

    public ColdsteelHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.supertype.add(SuperType.SNOW);

        // Coldsteel Heart enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // As Coldsteel Heart enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral), null, "As {this} enters the battlefield, choose a color.", null));
        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));

    }

    private ColdsteelHeart(final ColdsteelHeart card) {
        super(card);
    }

    @Override
    public ColdsteelHeart copy() {
        return new ColdsteelHeart(this);
    }
}
