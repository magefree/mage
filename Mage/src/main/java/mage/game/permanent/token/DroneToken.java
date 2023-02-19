package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author @stwalsh4118
 */
public class DroneToken extends TokenImpl {

    public DroneToken() {
        super("Drone Token", "2/2 colorless Drone artifact creature token with deathtouch and \"When this creature leaves the battlefield, each opponent loses 2 life and you gain 2 life.\"");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.DRONE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(DeathtouchAbility.getInstance());

        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        addAbility(ability);

    }

    private DroneToken(final DroneToken token) {
        super(token);
    }

    @Override
    public DroneToken copy() {
        return new DroneToken(this);
    }
}
