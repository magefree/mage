package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeldonStrikeTeam extends CardImpl {

    public KeldonStrikeTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {1}{W}
        this.addAbility(new KickerAbility("{1}{W}"));

        // When Keldon Strike Team enters the battlefield, if it was kicked, create two 1/1 white Soldier creature tokens.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2)),
                KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, " +
                "create two 1/1 white Soldier creature tokens."
        ));

        // As long as Keldon Strike Team entered the battlefield this turn, creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), KeldonStrikeTeamCondition.instance, "as long as {this} " +
                "entered the battlefield this turn, creatures you control have haste"
        )));
    }

    private KeldonStrikeTeam(final KeldonStrikeTeam card) {
        super(card);
    }

    @Override
    public KeldonStrikeTeam copy() {
        return new KeldonStrikeTeam(this);
    }
}

enum KeldonStrikeTeamCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getTurnsOnBattlefield)
                .orElse(-1) == 0;
    }
}
