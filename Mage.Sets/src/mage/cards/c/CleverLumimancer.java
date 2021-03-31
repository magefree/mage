package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleverLumimancer extends CardImpl {

    public CleverLumimancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Clever Lumimancer gets +2/+2 until end of turn.
        this.addAbility(new MagecraftAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));
    }

    private CleverLumimancer(final CleverLumimancer card) {
        super(card);
    }

    @Override
    public CleverLumimancer copy() {
        return new CleverLumimancer(this);
    }
}
