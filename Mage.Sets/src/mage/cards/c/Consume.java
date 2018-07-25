package mage.cards.c;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class Consume extends CardImpl {

    public Consume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");
        

        // As an additional cost to cast Consume, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public Consume(final Consume card) {
        super(card);
    }

    @Override
    public Consume copy() {
        return new Consume(this);
    }
}
