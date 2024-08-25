package mage.cards.f;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeedTheCycle extends CardImpl {

    public FeedTheCycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, forage or pay {B}.
        this.getSpellAbility().addCost(new OrCost(
                "forage or pay {B}", new ForageCost(), new ManaCostsImpl<>("{B}")
        ));

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FeedTheCycle(final FeedTheCycle card) {
        super(card);
    }

    @Override
    public FeedTheCycle copy() {
        return new FeedTheCycle(this);
    }
}
