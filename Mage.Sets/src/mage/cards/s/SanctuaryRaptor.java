package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctuaryRaptor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 2);
    private static final Hint hint
            = new ValueHint("Tokens you control", new PermanentsOnBattlefieldCount(filter));

    public SanctuaryRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sanctuary Raptor attacks, if you control three or more tokens, Sanctuary Raptor gets +2/+0 and gains first strike until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new BoostSourceEffect(2, 0, Duration.EndOfTurn), false
                ), condition, "Whenever {this} attacks, if you control three or more tokens, " +
                "{this} gets +2/+0 and gains first strike until end of turn."
        );
        ability.addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability.addHint(hint));
    }

    private SanctuaryRaptor(final SanctuaryRaptor card) {
        super(card);
    }

    @Override
    public SanctuaryRaptor copy() {
        return new SanctuaryRaptor(this);
    }
}
