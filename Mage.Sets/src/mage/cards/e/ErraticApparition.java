package mage.cards.e;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErraticApparition extends CardImpl {

    public ErraticApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, Erratic Apparition gets +1/+1 until end of turn.
        this.addAbility(new EerieAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));
    }

    private ErraticApparition(final ErraticApparition card) {
        super(card);
    }

    @Override
    public ErraticApparition copy() {
        return new ErraticApparition(this);
    }
}
