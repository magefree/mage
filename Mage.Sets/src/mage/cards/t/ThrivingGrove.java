package mage.cards.t;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.GreenManaAbility;
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
public final class ThrivingGrove extends CardImpl {

    public ThrivingGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Thriving Grove enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Thriving Grove enters the battlefield, choose a color other than green.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral, "Green")));

        // {T}: Add {G} or one mana of the chosen color.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ThrivingGrove(final ThrivingGrove card) {
        super(card);
    }

    @Override
    public ThrivingGrove copy() {
        return new ThrivingGrove(this);
    }
}
