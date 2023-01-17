package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.CantBeTargetedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;

/**
 *
 * @author # -*- AhmadYProjects-*-
 */
public final class ThrunBreakerOfSilence extends CardImpl {



    public ThrunBreakerOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantBeCounteredSourceEffect()));
        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Thrun, Breaker of Silence can't be the target of nongreen spells your opponents control or abilities from nongreen sources your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedSourceEffect(StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS_NON_GREEN,Duration.WhileOnBattlefield)));
        // As long as it's your turn, Thrun has indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        MyTurnCondition.instance,
                        "As long as it's your turn, {this} has indestructible"))
                .addHint(MyTurnHint.instance));
    }

    private ThrunBreakerOfSilence(final ThrunBreakerOfSilence card) {
        super(card);
    }

    @Override
    public ThrunBreakerOfSilence copy() {
        return new ThrunBreakerOfSilence(this);
    }
}
