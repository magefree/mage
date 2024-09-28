package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranGuardmouse extends CardImpl {

    public VeteranGuardmouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/W}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Valiant -- Whenever Veteran Guardmouse becomes the target of a spell or ability you control for the first time each turn, it gets +1/+0 and gains first strike until end of turn. Scry 1.
        Ability ability = new ValiantTriggeredAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("it gets +1/+0"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        ability.addEffect(new ScryEffect(1, false));
        this.addAbility(ability);
    }

    private VeteranGuardmouse(final VeteranGuardmouse card) {
        super(card);
    }

    @Override
    public VeteranGuardmouse copy() {
        return new VeteranGuardmouse(this);
    }
}
