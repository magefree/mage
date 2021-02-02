
package mage.cards.k;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class KuldothaRebirth extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public KuldothaRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 3));
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
    }

    private KuldothaRebirth(final KuldothaRebirth card) {
        super(card);
    }

    @Override
    public KuldothaRebirth copy() {
        return new KuldothaRebirth(this);
    }

}
