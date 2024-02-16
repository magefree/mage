
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.permanent.token.TrooperToken;

/**
 *
 * @author Styxo
 */
public final class KaminoCloningFacility extends CardImpl {

    private static final FilterSpell FILTER = new FilterSpell("a Trooper spell");

    static {
        FILTER.add(SubType.TROOPER.getPredicate());
    }

    public KaminoCloningFacility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T} Add one mana of any color. Spend this mana only to cast a Trooper spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(FILTER), true));

        // {5}, {T}: Create a 1/1 white Trooper creature tokens.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TrooperToken(), 1), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KaminoCloningFacility(final KaminoCloningFacility card) {
        super(card);
    }

    @Override
    public KaminoCloningFacility copy() {
        return new KaminoCloningFacility(this);
    }
}
