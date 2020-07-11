package mage.cards.t;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.RedManaAbility;
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
public final class ThrivingBluff extends CardImpl {

    public ThrivingBluff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Thriving Bluff enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Thriving Bluff enters the battlefield, choose a color other than red.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral, "Red")));

        // {T}: Add {R} or one mana of the chosen color.
        this.addAbility(new RedManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ThrivingBluff(final ThrivingBluff card) {
        super(card);
    }

    @Override
    public ThrivingBluff copy() {
        return new ThrivingBluff(this);
    }
}
