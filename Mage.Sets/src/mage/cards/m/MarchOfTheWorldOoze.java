package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentNoManaSpentTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ElephantToken;

/**
 *
 * @author Jmlundeen
 */
public final class MarchOfTheWorldOoze extends CardImpl {
    public static final FilterSpell filter = new FilterSpell("a spell, if it's not their turn");

    static {
        filter.add(TargetController.INACTIVE.getControllerPredicate());
    }
    public MarchOfTheWorldOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}{G}");
        

        // Creatures you control have base power and toughness 6/6 and are Oozes in addition to their other types.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessAllEffect(
                6, 6, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURE));
        ability.addEffect(new AddCardSubtypeAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE, SubType.OOZE, null)
                        .concatBy("and"));
        this.addAbility(ability);
        // Whenever an opponent casts a spell, if it's not their turn, you create a 3/3 green Elephant creature token.
        Ability ability2 = new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new ElephantToken())
                .setText("you create a 3/3 green Elephant creature token"),
                filter, false);
        this.addAbility(ability2);
    }

    private MarchOfTheWorldOoze(final MarchOfTheWorldOoze card) {
        super(card);
    }

    @Override
    public MarchOfTheWorldOoze copy() {
        return new MarchOfTheWorldOoze(this);
    }
}
