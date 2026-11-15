
package mage.cards.g;

import mage.MageInt;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author Styxo & L_J
 */
public final class GabrielAngelfire extends CardImpl {

    public GabrielAngelfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, choose flying, first strike, trample, or rampage 3. Gabriel Angelfire gains that ability until your next upkeep.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainsChoiceOfAbilitiesEffect(
                GainsChoiceOfAbilitiesEffect.TargetType.Source, "{this}", true, Duration.UntilYourNextUpkeepStep,
                FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), TrampleAbility.getInstance(), new RampageAbility(3)
        ).setText("choose flying, first strike, trample, or rampage 3. "
                + "{this} gains that ability until your next upkeep")));
    }

    private GabrielAngelfire(final GabrielAngelfire card) {
        super(card);
    }

    @Override
    public GabrielAngelfire copy() {
        return new GabrielAngelfire(this);
    }
}
