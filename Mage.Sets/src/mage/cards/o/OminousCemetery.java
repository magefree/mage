package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OminousCemetery extends CardImpl {

    public OminousCemetery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}, Exile Ominous Cemetery: Target creature's owner shuffles it into their library.
        Ability ability = new SimpleActivatedAbility(
                new ShuffleIntoLibraryTargetEffect()
                        .setText("target creature's owner shuffles it into their library"),
                new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OminousCemetery(final OminousCemetery card) {
        super(card);
    }

    @Override
    public OminousCemetery copy() {
        return new OminousCemetery(this);
    }
}
