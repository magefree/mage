package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author NinthWorld
 */
public final class Thor extends CardImpl {

    public Thor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Thor deals 3 damage to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new ManaCostsImpl("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    public Thor(final Thor card) {
        super(card);
    }

    @Override
    public Thor copy() {
        return new Thor(this);
    }
}
