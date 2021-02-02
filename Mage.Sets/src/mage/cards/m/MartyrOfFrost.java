
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
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class MartyrOfFrost extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("X blue cards from your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public MartyrOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, Reveal X blue cards from your hand, Sacrifice Martyr of Frost: Counter target spell unless its controller pays {X}.
        Effect effect = new CounterUnlessPaysEffect(RevealTargetFromHandCostCount.instance);
        effect.setText("Counter target spell unless its controller pays {X}.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private MartyrOfFrost(final MartyrOfFrost card) {
        super(card);
    }

    @Override
    public MartyrOfFrost copy() {
        return new MartyrOfFrost(this);
    }
}
