package mage.cards.z;

import mage.abilities.effects.common.CreateTokenControllerTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
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
public final class ZukosExile extends CardImpl {

    public ZukosExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}");

        this.subtype.add(SubType.LESSON);

        // Exile target artifact, creature, or enchantment. Its controller creates a Clue token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetEffect(new ClueArtifactToken()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT));
    }

    private ZukosExile(final ZukosExile card) {
        super(card);
    }

    @Override
    public ZukosExile copy() {
        return new ZukosExile(this);
    }
}
