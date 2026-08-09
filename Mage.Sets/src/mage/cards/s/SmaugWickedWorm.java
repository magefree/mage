package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SmaugWickedWorm extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public SmaugWickedWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Smaug enters, create X tapped Treasure tokens, where X is the number of artifacts your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), xValue, true, false)
        ).addHint(new ValueHint("Artifacts your opponents control", xValue)));

        // Whenever you cast a spell, if mana from a Treasure was spent to cast it, you draw a card and lose 1 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL
        ).withInterveningIf(SmaugWickedWormCondition.instance);
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private SmaugWickedWorm(final SmaugWickedWorm card) {
        super(card);
    }

    @Override
    public SmaugWickedWorm copy() {
        return new SmaugWickedWorm(this);
    }
}

enum SmaugWickedWormCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) source.getEffects().get(0).getValue("spellCast");
        return spell != null && ManaPaidSourceWatcher.getTreasurePaid(spell.getSourceId(), game) > 0;
    }

    @Override
    public String toString() {
        return "mana from a Treasure was spent to cast it";
    }
}
