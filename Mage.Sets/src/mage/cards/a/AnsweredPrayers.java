package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnsweredPrayers extends CardImpl {

    public AnsweredPrayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // Whenever a creature enters the battlefield under your control, you gain 1 life. If Answered Prayers isn't a creature, it becomes a 3/3 Angel creature with flying in addition to its other types until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        new GainLifeEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE
                ), AnsweredPrayersCondition.instance, "Whenever a creature enters the battlefield " +
                "under your control, you gain 1 life. If {this} isn't a creature, " +
                "it becomes a 3/3 Angel creature with flying in addition to its other types until end of turn."
        );
        ability.addEffect(new BecomesCreatureSourceEffect(
                new AnsweredPrayersToken(), "enchantment", Duration.EndOfTurn
        ));
        this.addAbility(ability);
    }

    private AnsweredPrayers(final AnsweredPrayers card) {
        super(card);
    }

    @Override
    public AnsweredPrayers copy() {
        return new AnsweredPrayers(this);
    }
}

enum AnsweredPrayersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        return permanent != null && !permanent.isCreature();
    }
}

class AnsweredPrayersToken extends TokenImpl {

    AnsweredPrayersToken() {
        super("", "3/3 Angel creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ANGEL);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    private AnsweredPrayersToken(final AnsweredPrayersToken token) {
        super(token);
    }

    public AnsweredPrayersToken copy() {
        return new AnsweredPrayersToken(this);
    }
}
