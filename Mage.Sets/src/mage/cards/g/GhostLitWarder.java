
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class GhostLitWarder extends CardImpl {

    public GhostLitWarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{U}, {tap}: Counter target spell unless its controller pays {2}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new ManaCostsImpl<>("{2}")), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
        // Channel - {3}{U}, Discard Ghost-Lit Warder: Counter target spell unless its controller pays {4}.
        Ability ability2 = new ChannelAbility("{3}{U}", new CounterUnlessPaysEffect(new ManaCostsImpl<>("{4}")));
        ability2.addTarget(new TargetSpell());
        this.addAbility(ability2);
        
    }

    private GhostLitWarder(final GhostLitWarder card) {
        super(card);
    }

    @Override
    public GhostLitWarder copy() {
        return new GhostLitWarder(this);
    }
}
