
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ayratn
 */
public final class ScreechingSilcaw extends CardImpl {

    private static final String text = "Metalcraft - Whenever Screeching Silcaw deals combat damage to a player, if you control three or more artifacts, that player puts the top four cards of their library into their graveyard.";

    public ScreechingSilcaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        TriggeredAbility conditional = new ConditionalTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new PutLibraryIntoGraveTargetEffect(4), false, true),
                MetalcraftCondition.instance, text);
        this.addAbility(conditional);
    }

    public ScreechingSilcaw(final ScreechingSilcaw card) {
        super(card);
    }

    @Override
    public ScreechingSilcaw copy() {
        return new ScreechingSilcaw(this);
    }
}
