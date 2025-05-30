package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemurBattlecrier extends CardImpl {

    public TemurBattlecrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // During your turn, spells you cast cost {1} less to cast for each creature you control with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(new TemurBattlecrierEffect()).addHint(TemurBattlecrierEffect.getHint()));
    }

    private TemurBattlecrier(final TemurBattlecrier card) {
        super(card);
    }

    @Override
    public TemurBattlecrier copy() {
        return new TemurBattlecrier(this);
    }
}

class TemurBattlecrierEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final Hint hint = new ValueHint(
            "Creatures you control with power 4 or greater", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    TemurBattlecrierEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "during your turn, spells you cast cost {1} less to cast " +
                "for each creature you control with power 4 or greater";
    }

    private TemurBattlecrierEffect(final TemurBattlecrierEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Ability spellAbility = abilityToModify;
        if (spellAbility == null) {
            return false;
        }
        int amount = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (amount > 0) {
            CardUtil.reduceCost(spellAbility, amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())
                || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null;
    }

    @Override
    public TemurBattlecrierEffect copy() {
        return new TemurBattlecrierEffect(this);
    }
}
