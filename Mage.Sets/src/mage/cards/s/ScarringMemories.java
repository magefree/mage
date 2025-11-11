package mage.cards.s;

import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScarringMemories extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("you control an attacking legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public ScarringMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        this.subtype.add(SubType.LESSON);

        // You may cast this spell as though it had flash if you control an attacking legendary creature.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
                condition, "you may cast this spell as though it had flash if you control an attacking legendary creature"
        ));

        // Target opponent sacrifices a creature of their choice, discards a card, and loses 3 life.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1).setText(", discards a card"));
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3).setText(", and loses 3 life"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ScarringMemories(final ScarringMemories card) {
        super(card);
    }

    @Override
    public ScarringMemories copy() {
        return new ScarringMemories(this);
    }
}
