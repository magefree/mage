
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class MonCalamariCruiser extends CardImpl {

    public MonCalamariCruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever Mon Calamari Cruiser deals combat damage to a player, you may draw a card. 
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true));

    }

    private MonCalamariCruiser(final MonCalamariCruiser card) {
        super(card);
    }

    @Override
    public MonCalamariCruiser copy() {
        return new MonCalamariCruiser(this);
    }
}
