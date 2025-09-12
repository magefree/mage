package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfTheDark extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.GLIMMER, "defending player controls no Glimmer creatures");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter, ComparisonType.EQUAL_TO, 0, false
    );

    public FearOfTheDark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Fear of the Dark attacks, if defending player controls no Glimmer creatures, it gains menace and deathtouch until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("it gains menace")).withInterveningIf(condition);
        ability.addEffect(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setText("and deathtouch until end of turn"));
        this.addAbility(ability);
    }

    private FearOfTheDark(final FearOfTheDark card) {
        super(card);
    }

    @Override
    public FearOfTheDark copy() {
        return new FearOfTheDark(this);
    }
}
