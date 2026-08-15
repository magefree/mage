package mage.cards.t;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrivingMoor extends CardImpl {

    public ThrivingMoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Thriving Moor enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Thriving Moor enters the battlefield, choose a color other than black.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral, "Black")));

        // {T}: Add {B} or one mana of the chosen color.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ThrivingMoor(final ThrivingMoor card) {
        super(card);
    }

    @Override
    public ThrivingMoor copy() {
        return new ThrivingMoor(this);
    }
}
