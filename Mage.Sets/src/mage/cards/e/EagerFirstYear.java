package mage.cards.e;

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
public final class EagerFirstYear extends CardImpl {

    public EagerFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft - Whenever you cast or copy an instant or sorcery spell, Eager First-Year gets +1/+0 until end of turn.
        this.addAbility(new MagecraftAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private EagerFirstYear(final EagerFirstYear card) {
        super(card);
    }

    @Override
    public EagerFirstYear copy() {
        return new EagerFirstYear(this);
    }
}
