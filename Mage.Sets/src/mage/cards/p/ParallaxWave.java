
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ParallaxWave extends CardImpl {

    public ParallaxWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Fading 5
        this.addAbility(new FadingAbility(5, this));

        // Remove a fade counter from Parallax Wave: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Parallax Wave leaves the battlefield, each player returns to the battlefield all cards they own exiled with Parallax Wave.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));

    }

    private ParallaxWave(final ParallaxWave card) {
        super(card);
    }

    @Override
    public ParallaxWave copy() {
        return new ParallaxWave(this);
    }
}
