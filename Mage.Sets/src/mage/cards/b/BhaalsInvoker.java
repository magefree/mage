package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BhaalsInvoker extends CardImpl {

    public BhaalsInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Scorching Ray — {8}: Bhaal's Invoker deals 4 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(
                new DamagePlayersEffect(4, TargetController.OPPONENT), new GenericManaCost(8)
        ).withFlavorWord("Scorching Ray"));
    }

    private BhaalsInvoker(final BhaalsInvoker card) {
        super(card);
    }

    @Override
    public BhaalsInvoker copy() {
        return new BhaalsInvoker(this);
    }
}
