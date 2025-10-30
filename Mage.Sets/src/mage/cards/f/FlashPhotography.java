package mage.cards.f;

import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlashPhotography extends CardImpl {

    private static final Condition condition = new SourceTargetsPermanentCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT);

    public FlashPhotography(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // You may cast this spell as though it had flash if it targets a permanent you control.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(
                condition, "you may cast this spell as though it had flash if it targets a permanent you control"
        ));

        // Create a token that's a copy of target permanent.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Flashback {4}{U}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{U}{U}")));
    }

    private FlashPhotography(final FlashPhotography card) {
        super(card);
    }

    @Override
    public FlashPhotography copy() {
        return new FlashPhotography(this);
    }
}
