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
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ParallaxTide extends CardImpl {

    public ParallaxTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // Fading 5
        this.addAbility(new FadingAbility(5, this));

        // Remove a fade counter from Parallax Tide: Exile target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance()));
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // When Parallax Tide leaves the battlefield, each player returns to the battlefield all cards they own exiled with Parallax Tide.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));
    }

    private ParallaxTide(final ParallaxTide card) {
        super(card);
    }

    @Override
    public ParallaxTide copy() {
        return new ParallaxTide(this);
    }
}
