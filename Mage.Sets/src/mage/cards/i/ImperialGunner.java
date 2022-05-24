package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class ImperialGunner extends CardImpl {

    private static final FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker(
            "target player, planeswalker or Starship creature", SubType.STARSHIP);

    public ImperialGunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1},{T}: Imperial Gunner deals 1 damage to target player or Starship creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}"));
        ability.addTarget(new TargetAnyTarget(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ImperialGunner(final ImperialGunner card) {
        super(card);
    }

    @Override
    public ImperialGunner copy() {
        return new ImperialGunner(this);
    }
}
