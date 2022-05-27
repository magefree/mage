package mage.cards.m;

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
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManorGate extends CardImpl {

    public ManorGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.GATE);

        // Manor Gate enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // As Manor Gate enters the battlefield, choose a color other than green.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral, "Green")));

        // {T}: Add {G} or one mana of the chosen color.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private ManorGate(final ManorGate card) {
        super(card);
    }

    @Override
    public ManorGate copy() {
        return new ManorGate(this);
    }
}
