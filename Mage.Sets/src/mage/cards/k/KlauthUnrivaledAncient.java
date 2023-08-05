package mage.cards.k;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KlauthUnrivaledAncient extends CardImpl {

    public KlauthUnrivaledAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Klauth, Unrivaled Ancient attacks, add X mana in any combination of colors, where X is the total power of attacking creatures. Spend this mana only to cast spells. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new KlauthUnrivaledAncientEffect()));
    }

    private KlauthUnrivaledAncient(final KlauthUnrivaledAncient card) {
        super(card);
    }

    @Override
    public KlauthUnrivaledAncient copy() {
        return new KlauthUnrivaledAncient(this);
    }
}

class KlauthUnrivaledAncientEffect extends OneShotEffect {

    private static final List<String> manaSymbols = Arrays.asList("W", "U", "B", "R", "G");

    KlauthUnrivaledAncientEffect() {
        super(Outcome.Benefit);
        staticText = "add X mana in any combination of colors, where X is the total power of attacking creatures. " +
                "Spend this mana only to cast spells. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private KlauthUnrivaledAncientEffect(final KlauthUnrivaledAncientEffect effect) {
        super(effect);
    }

    @Override
    public KlauthUnrivaledAncientEffect copy() {
        return new KlauthUnrivaledAncientEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int attackerPower = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_ATTACKING_CREATURES,
                        source.getControllerId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        List<Integer> manaList = player.getMultiAmount(
                outcome, manaSymbols, attackerPower, attackerPower, MultiAmountType.MANA, game
        );
        player.getManaPool().addMana(
                new KlauthUnrivaledAncientConditionalMana(manaList), game, source, true
        );
        return true;
    }
}

class KlauthUnrivaledAncientConditionalMana extends ConditionalMana {

    public KlauthUnrivaledAncientConditionalMana(List<Integer> manaList) {
        super(new Mana(
                manaList.get(0),
                manaList.get(1),
                manaList.get(2),
                manaList.get(3),
                manaList.get(4),
                0, 0, 0
        ));
        addCondition(new KlauthUnrivaledAncientManaCondition());
    }
}

class KlauthUnrivaledAncientManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        return source instanceof SpellAbility;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
