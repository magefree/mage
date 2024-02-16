
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public final class Woodripper extends CardImpl {

    public Woodripper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Fading 3
        this.addAbility(new FadingAbility(3, this));
        // {1}, Remove a fade counter from Woodripper: Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.FADE.createInstance()));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private Woodripper(final Woodripper card) {
        super(card);
    }

    @Override
    public Woodripper copy() {
        return new Woodripper(this);
    }
}
