package mage.cards.c;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CultHealer extends CardImpl {

    public CultHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, Cult Healer gains lifelink until end of turn.
        this.addAbility(new EerieAbility(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn)));
    }

    private CultHealer(final CultHealer card) {
        super(card);
    }

    @Override
    public CultHealer copy() {
        return new CultHealer(this);
    }
}
