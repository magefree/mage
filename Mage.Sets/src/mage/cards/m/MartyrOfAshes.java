
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RevealTargetFromHandCostCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class MartyrOfAshes extends CardImpl {
    
    private static final FilterCard filterHand = new FilterCard("X red cards from your hand");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creatures without flying");

    static {
        filterHand.add(new ColorPredicate(ObjectColor.RED));
        filterCreature.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }
    
    public MartyrOfAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, Reveal X red cards from your hand, Sacrifice Martyr of Ashes: Martyr of Ashes deals X damage to each creature without flying.
        Effect effect = new DamageAllEffect(RevealTargetFromHandCostCount.instance, filterCreature);
        effect.setText("Martyr of Ashes deals X damage to each creature without flying.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filterHand)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MartyrOfAshes(final MartyrOfAshes card) {
        super(card);
    }

    @Override
    public MartyrOfAshes copy() {
        return new MartyrOfAshes(this);
    }
}
