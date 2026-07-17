package mage.cards.g;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Spirit22RedWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GroupProject extends CardImpl {

    public GroupProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Create a 2/2 red and white Spirit creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Spirit22RedWhiteToken()));

        // Flashback--Tap three untapped creatures you control.
        this.addAbility(new FlashbackAbility(
                this, new TapTargetCost(3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES)
        ));
    }

    private GroupProject(final GroupProject card) {
        super(card);
    }

    @Override
    public GroupProject copy() {
        return new GroupProject(this);
    }
}
