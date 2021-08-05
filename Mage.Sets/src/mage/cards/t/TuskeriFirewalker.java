package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.BoastAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class TuskeriFirewalker extends CardImpl {

    public TuskeriFirewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Boast — {1}: Exile the top card of your library. You may play that card this turn.
        this.addAbility(new BoastAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), new GenericManaCost(1)));
    }

    private TuskeriFirewalker(final TuskeriFirewalker card) {
        super(card);
    }

    @Override
    public TuskeriFirewalker copy() {
        return new TuskeriFirewalker(this);
    }
}
