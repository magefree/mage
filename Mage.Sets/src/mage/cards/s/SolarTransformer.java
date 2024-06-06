package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolarTransformer extends CardImpl {

    public SolarTransformer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Solar Transformer enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Solar Transformer enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay {E}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayEnergyCost(1));
        this.addAbility(ability);
    }

    private SolarTransformer(final SolarTransformer card) {
        super(card);
    }

    @Override
    public SolarTransformer copy() {
        return new SolarTransformer(this);
    }
}
