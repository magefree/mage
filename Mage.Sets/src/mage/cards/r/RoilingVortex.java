package mage.cards.r;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoilingVortex extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell");

    static {
        filter.add(RoilingVortexPredicate.instance);
    }

    public RoilingVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of each player's upkeep, Roiling Vortex deals 1 damage to them.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "them"),
                TargetController.ANY, false, true
        ));

        // Whenever a player casts a spell, if no mana was spent to cast that spell, Roiling Vortex deals 5 damage to that player.
        this.addAbility(new SpellCastAllTriggeredAbility(new DamageTargetEffect(
                5, true, "that player",
                "if no mana was spent to cast that spell, {this}"
        ), filter, false, SetTargetPointer.PLAYER));

        // {R}: Your opponents can't gain life this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantGainLifeAllEffect(Duration.EndOfTurn, TargetController.OPPONENT),
                new ColoredManaCost(ColoredManaSymbol.R)
        ));
    }

    private RoilingVortex(final RoilingVortex card) {
        super(card);
    }

    @Override
    public RoilingVortex copy() {
        return new RoilingVortex(this);
    }
}

enum RoilingVortexPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input.getStackAbility().getManaCostsToPay().getPayment().count() == 0;
    }
}
