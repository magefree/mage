package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrackOpen extends CardImpl {

    public CrackOpen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Destroy target artifact or enchantment. Create a Treasure token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private CrackOpen(final CrackOpen card) {
        super(card);
    }

    @Override
    public CrackOpen copy() {
        return new CrackOpen(this);
    }
}
