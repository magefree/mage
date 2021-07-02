package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class DungeonDescent extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("untapped legendary creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public DungeonDescent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Dungeon Descent enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}, Tap an untapped legendary creature you control: Venture into the dungeon. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new VentureIntoTheDungeonEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    private DungeonDescent(final DungeonDescent card) {
        super(card);
    }

    @Override
    public DungeonDescent copy() {
        return new DungeonDescent(this);
    }
}
