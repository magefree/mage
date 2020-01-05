package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShoalKraken extends CardImpl {

    public ShoalKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Constellation — Whenever an enchantment enters the battlefield under your control, you may a draw card. If you do, discard a card.
        this.addAbility(new ConstellationAbility(
                new DrawDiscardControllerEffect(1, 1, true), false, false
        ));
    }

    private ShoalKraken(final ShoalKraken card) {
        super(card);
    }

    @Override
    public ShoalKraken copy() {
        return new ShoalKraken(this);
    }
}
