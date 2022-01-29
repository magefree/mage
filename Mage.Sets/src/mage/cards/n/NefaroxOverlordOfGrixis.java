
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

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
        this.addAbility(new AttacksAloneSourceTriggeredAbility(new SacrificeEffect(
            StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, 1, "defending player")));
    }

    private NefaroxOverlordOfGrixis(final NefaroxOverlordOfGrixis card) {
        super(card);
    }

    @Override
    public NefaroxOverlordOfGrixis copy() {
        return new NefaroxOverlordOfGrixis(this);
    }
}
