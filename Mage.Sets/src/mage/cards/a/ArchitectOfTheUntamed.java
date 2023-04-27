package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ArchitectOfTheUntamedBeastToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ArchitectOfTheUntamed extends CardImpl {

    public ArchitectOfTheUntamed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a land enters the battlefield under your control, you get {E}.
        this.addAbility(new LandfallAbility(new GetEnergyCountersControllerEffect(1)));

        // Pay {E}{E}{E}{E}{E}{E}{E}{E}: Create a 6/6 colorless Beast artifact creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new ArchitectOfTheUntamedBeastToken(), 1), new PayEnergyCost(8)
        ));
    }

    private ArchitectOfTheUntamed(final ArchitectOfTheUntamed card) {
        super(card);
    }

    @Override
    public ArchitectOfTheUntamed copy() {
        return new ArchitectOfTheUntamed(this);
    }
}
