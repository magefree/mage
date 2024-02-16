package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PanickedAltisaur extends CardImpl {

    public PanickedAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Panicked Altisaur deals 2 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), new TapSourceCost()));
    }

    private PanickedAltisaur(final PanickedAltisaur card) {
        super(card);
    }

    @Override
    public PanickedAltisaur copy() {
        return new PanickedAltisaur(this);
    }
}
