package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaretakersTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public CaretakersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever one or more tokens you control enter, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_PERMANENT_TOKEN
        ).setTriggerPhrase("Whenever one or more tokens you control enter, ").setTriggersLimitEachTurn(1));

        // {W}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{W}"));

        // When this Class becomes level 2, create a token that's a copy of target token you control.
        Ability ability = new BecomesClassLevelTriggeredAbility(new CreateTokenCopyTargetEffect(), 2);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {3}{W}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{W}"));

        // Creature tokens you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        ), 3)));
    }

    private CaretakersTalent(final CaretakersTalent card) {
        super(card);
    }

    @Override
    public CaretakersTalent copy() {
        return new CaretakersTalent(this);
    }
}
