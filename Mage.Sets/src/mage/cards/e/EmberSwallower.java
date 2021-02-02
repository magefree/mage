
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class EmberSwallower extends CardImpl {

    public EmberSwallower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {5}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}", 3));
        // When Ember Swallower becomes monstrous, each player sacrifices three lands.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new SacrificeAllEffect(3, new FilterControlledLandPermanent("lands"))));
    }

    private EmberSwallower(final EmberSwallower card) {
        super(card);
    }

    @Override
    public EmberSwallower copy() {
        return new EmberSwallower(this);
    }
}
