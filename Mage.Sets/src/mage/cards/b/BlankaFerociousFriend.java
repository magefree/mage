package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlankaFerociousFriend extends CardImpl {

    public BlankaFerociousFriend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Rolling Attack—Blanka, Ferocious Friend has trample as long as you've cast three or more spells this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()), BlankaFerociousFriendCondition.instance,
                "{this} has trample as long as you've cast three or more spells this turn"
        )).withFlavorWord("Rolling Attack"));

        // Electric Thunder—Whenever Blanka becomes the target of a spell, he gets +2/+2 until end of turn and deals 2 damage to each opponent.
        Ability ability = new BecomesTargetTriggeredAbility(new BoostSourceEffect(
                2, 2, Duration.EndOfTurn
        ).setText("he gets +2/+2 until end of turn"), StaticFilters.FILTER_SPELL_A).setTriggerPhrase("Whenever {this} becomes the target of a spell, ");
        ability.addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT)
                .setText("and deals 2 damage to each opponent"));
        this.addAbility(ability.withFlavorWord("Electric Thunder"));
    }

    private BlankaFerociousFriend(final BlankaFerociousFriend card) {
        super(card);
    }

    @Override
    public BlankaFerociousFriend copy() {
        return new BlankaFerociousFriend(this);
    }
}

enum BlankaFerociousFriendCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) >= 3;
    }
}
