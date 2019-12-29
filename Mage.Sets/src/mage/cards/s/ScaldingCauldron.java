package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScaldingCauldron extends CardImpl {

    public ScaldingCauldron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {3}, {T}, Sacrifice Scalding Cauldron: It deals 3 damage to target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(3, "it"), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ScaldingCauldron(final ScaldingCauldron card) {
        super(card);
    }

    @Override
    public ScaldingCauldron copy() {
        return new ScaldingCauldron(this);
    }
}
