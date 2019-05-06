package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarnsBastion extends CardImpl {

    public KarnsBastion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}: Proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        Ability ability = new SimpleActivatedAbility(new ProliferateEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KarnsBastion(final KarnsBastion card) {
        super(card);
    }

    @Override
    public KarnsBastion copy() {
        return new KarnsBastion(this);
    }
}
