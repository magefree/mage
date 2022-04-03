
package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class SiegeOfTowers extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public SiegeOfTowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Replicate {1}{R}
        this.addAbility(new ReplicateAbility("{1}{R}"));

        // Target Mountain becomes a 3/1 creature. It's still a land.
        Effect effect = new BecomesCreatureTargetEffect(new CreatureToken(3, 1), false, true, Duration.EndOfGame);
        effect.setText("Target Mountain becomes a 3/1 creature. It's still a land");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

    }

    private SiegeOfTowers(final SiegeOfTowers card) {
        super(card);
    }

    @Override
    public SiegeOfTowers copy() {
        return new SiegeOfTowers(this);
    }
}