package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ElephantToken;

import java.util.UUID;

/**
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
                6, 6, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        ability.addEffect(new AddCardSubtypeAllEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES, SubType.OOZE, null
        ).setText("and are Oozes in addition to their other types"));
        this.addAbility(ability);

        // Whenever an opponent casts a spell, if it's not their turn, you create a 3/3 green Elephant creature token.
        Ability ability2 = new SpellCastOpponentTriggeredAbility(
                new CreateTokenEffect(new ElephantToken())
                        .setText("you create a 3/3 green Elephant creature token"),
                filter, false
        );
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
