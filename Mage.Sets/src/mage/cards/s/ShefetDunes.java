
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class ShefetDunes extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");

    static {
        filterDesertPermanent.add(SubType.DESERT.getPredicate());
    }

    public ShefetDunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Pay 1 life: Add {W}.
        Ability ability = new WhiteManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {2}{W}{W}, {T}, Sacrifice a Desert: Creatures you control get +1/+1 until end of turn. Activate this ability only any time you could cast a sorcery.
        Ability ability2 = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{W}{W}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filterDesertPermanent, true)));
        this.addAbility(ability2);
    }

    private ShefetDunes(final ShefetDunes card) {
        super(card);
    }

    @Override
    public ShefetDunes copy() {
        return new ShefetDunes(this);
    }
}
