package mage.cards.w;

import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolfkinOutcast extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control a Wolf or Werewolf");
    private static final FilterPermanent filter2 = new FilterControlledPermanent("Wolf or Werewolf you control");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
        filter2.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(
            condition, "You control a Wolf or Werewolf"
    );

    public WolfkinOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{5}{G}",
                "Wedding Crasher",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Wolfkin Outcast
        this.getLeftHalfCard().setPT(5, 4);

        // This spell costs {2} less to cast if you control a Wolf or Werewolf.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true))
                .setRuleAtTheTop(true)
                .addHint(hint)
        );

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Wedding Crasher
        this.getRightHalfCard().setPT(6, 5);

        // Whenever Wedding Crasher or another Wolf or Werewolf you control dies, draw a card.
        this.getRightHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter2
        ));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private WolfkinOutcast(final WolfkinOutcast card) {
        super(card);
    }

    @Override
    public WolfkinOutcast copy() {
        return new WolfkinOutcast(this);
    }
}
