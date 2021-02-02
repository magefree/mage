
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class AgentOfErebos extends CardImpl {

    public AgentOfErebos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Agent of Erebos or another enchantment enters the battlefield under your control, exile all cards from target player's graveyard.
        Ability ability = new ConstellationAbility(new ExileGraveyardAllTargetPlayerEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AgentOfErebos(final AgentOfErebos card) {
        super(card);
    }

    @Override
    public AgentOfErebos copy() {
        return new AgentOfErebos(this);
    }
}
