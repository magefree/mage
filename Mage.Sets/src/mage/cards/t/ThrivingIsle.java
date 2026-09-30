package mage.cards.t;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.BlueManaAbility;
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
public final class ThrivingIsle extends CardImpl {

    public ThrivingIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Thriving Isle enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Thriving Isle enters the battlefield, choose a color other than blue.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral, "Blue")));

        // {T}: Add {U} or one mana of the chosen color.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ThrivingIsle(final ThrivingIsle card) {
        super(card);
    }

    @Override
    public ThrivingIsle copy() {
        return new ThrivingIsle(this);
    }
}
