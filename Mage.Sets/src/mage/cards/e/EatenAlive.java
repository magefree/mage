package mage.cards.e;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EatenAlive extends CardImpl {

    public EatenAlive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or pay {3}{B}.
        this.getSpellAbility().addCost(new OrCost(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent()),
                new ManaCostsImpl<>("{3}{B}"), "sacrifice a creature or pay {3}{B}"
        ));

        // Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private EatenAlive(final EatenAlive card) {
        super(card);
    }

    @Override
    public EatenAlive copy() {
        return new EatenAlive(this);
    }
}
