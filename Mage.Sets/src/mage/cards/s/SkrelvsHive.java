package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.PhyrexianMiteToken;

/**
 * @author TheElk801
 */
public final class SkrelvsHive extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(ToxicAbility.class));
    }

    public SkrelvsHive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your upkeep, you lose 1 life and create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeSourceControllerEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new CreateTokenEffect(new PhyrexianMiteToken()).concatBy("and"));
        this.addAbility(ability);

        // Corrupted -- As long as an opponent has three or more poison counters, creatures you control with toxic have lifelink.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAllEffect(
                        LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
                ), CorruptedCondition.instance, "as long as an opponent has three " +
                "or more poison counters, creatures you control with toxic have lifelink"
        )).setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
    }

    private SkrelvsHive(final SkrelvsHive card) {
        super(card);
    }

    @Override
    public SkrelvsHive copy() {
        return new SkrelvsHive(this);
    }
}
