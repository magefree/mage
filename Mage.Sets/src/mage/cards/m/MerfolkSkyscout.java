
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class MerfolkSkyscout extends CardImpl {

    public MerfolkSkyscout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        AttacksOrBlocksTriggeredAbility ability = new AttacksOrBlocksTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private MerfolkSkyscout(final MerfolkSkyscout card) {
        super(card);
    }

    @Override
    public MerfolkSkyscout copy() {
        return new MerfolkSkyscout(this);
    }
}
