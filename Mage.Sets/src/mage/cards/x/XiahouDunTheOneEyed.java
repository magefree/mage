
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class XiahouDunTheOneEyed extends CardImpl {

    private static final FilterCard filter = new FilterCard("a black card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public XiahouDunTheOneEyed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());

        // Sacrifice Xiahou Dun, the One-Eyed: Return target black card from your graveyard to your hand. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new ReturnFromGraveyardToHandTargetEffect(), new SacrificeSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private XiahouDunTheOneEyed(final XiahouDunTheOneEyed card) {
        super(card);
    }

    @Override
    public XiahouDunTheOneEyed copy() {
        return new XiahouDunTheOneEyed(this);
    }
}
