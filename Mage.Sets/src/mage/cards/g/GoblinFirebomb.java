package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinFirebomb extends CardImpl {

    public GoblinFirebomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {7}, {T}, Sacrifice Goblin Firebomb: Destroy target permanent.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private GoblinFirebomb(final GoblinFirebomb card) {
        super(card);
    }

    @Override
    public GoblinFirebomb copy() {
        return new GoblinFirebomb(this);
    }
}
