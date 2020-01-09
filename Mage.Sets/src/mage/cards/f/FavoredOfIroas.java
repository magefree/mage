package mage.cards.f;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FavoredOfIroas extends CardImpl {

    public FavoredOfIroas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation - Whenever an enchantment enters the battlefield under your control, Favored of Iroas gains double strike until end of turn.
        this.addAbility(new ConstellationAbility(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ), false, false));
    }

    private FavoredOfIroas(final FavoredOfIroas card) {
        super(card);
    }

    @Override
    public FavoredOfIroas copy() {
        return new FavoredOfIroas(this);
    }
}
