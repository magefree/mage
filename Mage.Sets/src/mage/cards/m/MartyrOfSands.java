
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.RevealTargetFromHandCostCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class MartyrOfSands extends CardImpl {

    private static final FilterCard filter = new FilterCard("X white cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public MartyrOfSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Reveal X white cards from your hand, Sacrifice Martyr of Sands: You gain three times X life.
        Effect effect = new GainLifeEffect(new MultipliedValue(RevealTargetFromHandCostCount.instance, 3));
        effect.setText("You gain three times X life.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}"));
        ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MartyrOfSands(final MartyrOfSands card) {
        super(card);
    }

    @Override
    public MartyrOfSands copy() {
        return new MartyrOfSands(this);
    }
}
