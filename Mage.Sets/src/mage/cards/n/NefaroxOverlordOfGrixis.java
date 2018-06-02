
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class NefaroxOverlordOfGrixis extends CardImpl {

    public NefaroxOverlordOfGrixis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Exalted
        this.addAbility(new ExaltedAbility());
        // Whenever Nefarox, Overlord of Grixis attacks alone, defending player sacrifices a creature.
        this.addAbility(new AttacksAloneTriggeredAbility(new SacrificeEffect(
            new FilterControlledCreaturePermanent("a creature"), 1, "defending player")));
    }

    public NefaroxOverlordOfGrixis(final NefaroxOverlordOfGrixis card) {
        super(card);
    }

    @Override
    public NefaroxOverlordOfGrixis copy() {
        return new NefaroxOverlordOfGrixis(this);
    }
}
