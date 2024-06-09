package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class LuckBobblehead extends CardImpl {

    public LuckBobblehead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.BOBBLEHEAD);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}, {T}: Roll X six-sided dice, where X is the number of Bobbleheads you control. Create a tapped Treasure token for each even result. If you rolled 6 exactly seven times, you win the game.
        Ability ability = new SimpleActivatedAbility(
                new LuckBobbleheadEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addHint(LuckBobbleheadEffect.hint);
        this.addAbility(ability);
    }

    private LuckBobblehead(final LuckBobblehead card) {
        super(card);
    }

    @Override
    public LuckBobblehead copy() {
        return new LuckBobblehead(this);
    }
}

class LuckBobbleheadEffect extends OneShotEffect {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.BOBBLEHEAD, "Bobbleheads you control"), null
    );
    static final Hint hint = new ValueHint("Bobbleheads you control", xValue);

    LuckBobbleheadEffect() {
        super(Outcome.Benefit);
        staticText = "Roll X six-sided dice, where X is the number of Bobbleheads you control. "
                + "Create a tapped Treasure token for each even result. "
                + "If you rolled 6 exactly seven times, you win the game";
    }

    private LuckBobbleheadEffect(final LuckBobbleheadEffect effect) {
        super(effect);
    }

    @Override
    public LuckBobbleheadEffect copy() {
        return new LuckBobbleheadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = xValue.calculate(game, source, this);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || amount <= 0) {
            return false;
        }
        List<Integer> rolls = controller.rollDice(Outcome.Benefit, source, game, 6, amount, 0);
        int evenRolls = rolls.stream().filter(i -> i != null && i % 2 == 0).mapToInt(i -> 1).sum();
        int sixRolls = rolls.stream().filter(i -> i == 6).mapToInt(i -> 1).sum();
        if (evenRolls > 0) {
            new CreateTokenEffect(new TreasureToken(), evenRolls, true).apply(game, source);
        }
        if (sixRolls == 7) {
            new WinGameSourceControllerEffect().apply(game, source);
        }
        return true;
    }
}