
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author North, Loki
 */
public final class ArchonOfJustice extends CardImpl {

    public ArchonOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new DiesSourceTriggeredAbility(new ExileTargetEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private ArchonOfJustice(final ArchonOfJustice card) {
        super(card);
    }

    @Override
    public ArchonOfJustice copy() {
        return new ArchonOfJustice(this);
    }
}
