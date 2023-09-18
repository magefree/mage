
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class GhostLitRedeemer extends CardImpl {

    public GhostLitRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}, {tap}: You gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // Channel - {1}{W}, Discard Ghost-Lit Redeemer: You gain 4 life.
        this.addAbility(new ChannelAbility("{1}{W}", new GainLifeEffect(4)));
    }

    private GhostLitRedeemer(final GhostLitRedeemer card) {
        super(card);
    }

    @Override
    public GhostLitRedeemer copy() {
        return new GhostLitRedeemer(this);
    }
}
