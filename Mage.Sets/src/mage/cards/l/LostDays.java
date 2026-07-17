package mage.cards.l;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostDays extends CardImpl {

    public LostDays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        this.subtype.add(SubType.LESSON);

        // The owner of target creature or enchantment puts it into their library second from the top or on the bottom. You create a Clue token.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(2, true));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("You"));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private LostDays(final LostDays card) {
        super(card);
    }

    @Override
    public LostDays copy() {
        return new LostDays(this);
    }
}
