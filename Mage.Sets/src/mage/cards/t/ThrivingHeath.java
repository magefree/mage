package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrivingHeath extends CardImpl {

    public ThrivingHeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // This land enters tapped. As it enters, choose a color other than white.
        Ability ability = new EntersBattlefieldTappedAbility();
        ability.addEffect(
            new ChooseColorEffect(Outcome.Neutral, "White")
                .setText("As it enters, choose a color other than white")
        );
        this.addAbility(ability);

        // {T}: Add {W} or one mana of the chosen color.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ThrivingHeath(final ThrivingHeath card) {
        super(card);
    }

    @Override
    public ThrivingHeath copy() {
        return new ThrivingHeath(this);
    }
}
