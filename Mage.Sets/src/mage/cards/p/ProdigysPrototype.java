package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.PilotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProdigysPrototype extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VEHICLE, "");

    public ProdigysPrototype(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever one or more Vehicles you control attack, create a 1/1 colorless Pilot creature token with "This creature crews Vehicles as though its power were 2 greater."
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new PilotToken()), 1, filter
        ).setTriggerPhrase("Whenever one or more Vehicles you control attack, "));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ProdigysPrototype(final ProdigysPrototype card) {
        super(card);
    }

    @Override
    public ProdigysPrototype copy() {
        return new ProdigysPrototype(this);
    }
}
