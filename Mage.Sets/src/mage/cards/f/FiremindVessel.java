package mage.cards.f;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.mana.AddManaOfTwoDifferentColorsEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiremindVessel extends CardImpl {

    public FiremindVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Firemind Vessel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add two mana of different colors.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfTwoDifferentColorsEffect(), new TapSourceCost()
        ));
    }

    private FiremindVessel(final FiremindVessel card) {
        super(card);
    }

    @Override
    public FiremindVessel copy() {
        return new FiremindVessel(this);
    }
}
