package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarinaVendrellsGrimoire extends CardImpl {

    public MarinaVendrellsGrimoire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Marina Vendrell's Grimoire enters, if you cast it, draw five cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(5))
                .withInterveningIf(CastFromEverywhereSourceCondition.instance));

        // You have no maximum hand size and don't lose the game for having 0 or less life.
        Ability ability = new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));
        ability.addEffect(new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)
                .setText("and don't lose the game for having 0 or less life"));
        this.addAbility(ability);

        // Whenever you gain life, draw that many cards.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedGainedLifeValue.MANY)
        ));

        // Whenever you lose life, discard that many cards. Then if you have no cards in hand, you lose the game.
        Ability ability2 = new LoseLifeTriggeredAbility(new DiscardControllerEffect(SavedLifeLossValue.MANY));
        ability2.addEffect(new ConditionalOneShotEffect(
                new LoseGameSourceControllerEffect(), HellbentCondition.instance,
                "Then if you have no cards in hand, you lose the game"
        ));
        this.addAbility(ability2);
    }

    private MarinaVendrellsGrimoire(final MarinaVendrellsGrimoire card) {
        super(card);
    }

    @Override
    public MarinaVendrellsGrimoire copy() {
        return new MarinaVendrellsGrimoire(this);
    }
}
