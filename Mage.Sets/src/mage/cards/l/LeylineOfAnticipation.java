
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfAnticipation extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public LeylineOfAnticipation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // If Leyline of Anticipation is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // You may cast spells as though they had flash. (You may cast them any time you could cast an instant.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private LeylineOfAnticipation(final LeylineOfAnticipation card) {
        super(card);
    }

    @Override
    public LeylineOfAnticipation copy() {
        return new LeylineOfAnticipation(this);
    }

}
