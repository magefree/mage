
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class LimestoneGolem extends CardImpl {

    public LimestoneGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}, Sacrifice Limestone Golem: Target player draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LimestoneGolem(final LimestoneGolem card) {
        super(card);
    }

    @Override
    public LimestoneGolem copy() {
        return new LimestoneGolem(this);
    }
}
