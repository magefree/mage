package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievesGuildEnforcer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ROGUE, "{this} or another Rogue");

    public ThievesGuildEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Thieves' Guild Enforcer or another Rogue enters the battlefield under your control, each opponent mills two cards.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new MillCardsEachPlayerEffect(
                        2, TargetController.OPPONENT
                ), filter, false, true
        ));

        // As long as an opponent has eight or more cards in their graveyard, Thieves' Guild Enforcer gets +2/+1 and has deathtouch.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
                CardsInOpponentGraveyardCondition.EIGHT, "as long as an opponent " +
                "has eight or more cards in their graveyard, {this} gets +2/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield
        ), CardsInOpponentGraveyardCondition.EIGHT, "and has deathtouch"));
        this.addAbility(ability.addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private ThievesGuildEnforcer(final ThievesGuildEnforcer card) {
        super(card);
    }

    @Override
    public ThievesGuildEnforcer copy() {
        return new ThievesGuildEnforcer(this);
    }
}
