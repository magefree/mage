package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeoninLightscribe extends CardImpl {

    public LeoninLightscribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, creatures you control get +1/+1 until end of turn,
        this.addAbility(new MagecraftAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)));
    }

    private LeoninLightscribe(final LeoninLightscribe card) {
        super(card);
    }

    @Override
    public LeoninLightscribe copy() {
        return new LeoninLightscribe(this);
    }
}
