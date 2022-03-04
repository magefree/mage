package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.PilotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShorikaiGenesisEngine extends CardImpl {

    public ShorikaiGenesisEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // {1}, {T}: Draw two cards, then discard a card. Create a 1/1 colorless Pilot creature token with "This creature crews Vehicles as though its power were 2 greater."
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(2, 1), new GenericManaCost(1)
        );
        ability.addEffect(new CreateTokenEffect(new PilotToken()));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Crew 8
        this.addAbility(new CrewAbility(8));

        // Shorikai, Genesis Engine can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private ShorikaiGenesisEngine(final ShorikaiGenesisEngine card) {
        super(card);
    }

    @Override
    public ShorikaiGenesisEngine copy() {
        return new ShorikaiGenesisEngine(this);
    }
}
