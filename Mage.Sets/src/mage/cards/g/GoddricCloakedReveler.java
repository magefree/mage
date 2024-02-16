package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GoddricCloakedReveler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DRAGON, "Dragons you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public GoddricCloakedReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Celebration -- As long as two or more nonland permanents entered the battlefield under your control this turn, Goddric, Cloaked Reveler is a Dragon with base power and toughness 4/4, flying, and "{R}: Dragons you control get +1/+0 until end of turn."
        Ability dragonFirebreath = new SimpleActivatedAbility(
                new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false),
                new ManaCostsImpl("{R}")
        );

        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(
                        new CreatureToken(4, 4,
                                "Dragon with base power and toughness 4/4, flying, "
                                        + "and \"{R}: Dragons you control get +1/+0 until end of turn.\"")
                                .withSubType(SubType.DRAGON)
                                .withAbility(FlyingAbility.getInstance())
                                .withAbility(dragonFirebreath),
                        CardType.CREATURE, Duration.WhileOnBattlefield
                ),
                CelebrationCondition.instance, "As long as two or more nonland permanents entered the battlefield under "
                + "your control this turn, {this} is a Dragon with base power and toughness 4/4, flying, "
                + "and \"{R}: Dragons you control get +1/+0 until end of turn.\""
        ));
        ability.setAbilityWord(AbilityWord.CELEBRATION);
        ability.addHint(CelebrationCondition.getHint());
        this.addAbility(ability, new CelebrationWatcher());
    }

    private GoddricCloakedReveler(final GoddricCloakedReveler card) {
        super(card);
    }

    @Override
    public GoddricCloakedReveler copy() {
        return new GoddricCloakedReveler(this);
    }
}
