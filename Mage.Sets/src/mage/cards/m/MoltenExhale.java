package mage.cards.m;

import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.common.BeholdDragonCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenExhale extends CardImpl {

    public MoltenExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // You may cast this spell as though it had flash if you behold a Dragon as an additional cost to cast it.
        this.addAbility(new PayMoreToCastAsThoughtItHadFlashAbility(
                this, new BeholdDragonCost(), "you may cast this spell as though " +
                "it had flash if you behold a Dragon as an additional cost to cast it"
        ));

        // Molten Exhale deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private MoltenExhale(final MoltenExhale card) {
        super(card);
    }

    @Override
    public MoltenExhale copy() {
        return new MoltenExhale(this);
    }
}
