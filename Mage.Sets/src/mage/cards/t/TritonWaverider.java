package mage.cards.t;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TritonWaverider extends CardImpl {

    public TritonWaverider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Constellation — Whenever an enchantment enters the battlefield under your control, Triton Waverider gains flying until end of turn.
        this.addAbility(new ConstellationAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), false, false));
    }

    private TritonWaverider(final TritonWaverider card) {
        super(card);
    }

    @Override
    public TritonWaverider copy() {
        return new TritonWaverider(this);
    }
}
