package mage.cards.b;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneShards extends CardImpl {

    public BoneShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or discard a card.
        this.getSpellAbility().addCost(new OrCost(
                "sacrifice a creature or discard a card", new SacrificeTargetCost(new TargetControlledCreaturePermanent()),
                new DiscardCardCost()
        ));

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BoneShards(final BoneShards card) {
        super(card);
    }

    @Override
    public BoneShards copy() {
        return new BoneShards(this);
    }
}
