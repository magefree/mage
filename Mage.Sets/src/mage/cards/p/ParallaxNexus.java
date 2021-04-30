package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ParallaxNexus extends CardImpl {

    public ParallaxNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Fading 5
        this.addAbility(new FadingAbility(5, this));

        // Remove a fade counter from Parallax Nexus: Target opponent exiles a card from their hand. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileFromZoneTargetEffect(Zone.HAND, true),
                new RemoveCountersSourceCost(CounterType.FADE.createInstance())
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Parallax Nexus leaves the battlefield, each player returns to their hand all cards they own exiled with Parallax Nexus.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileEffect(Zone.HAND).setText("each player returns to their hand all cards they own exiled with {this}"), false));
    }

    private ParallaxNexus(final ParallaxNexus card) {
        super(card);
    }

    @Override
    public ParallaxNexus copy() {
        return new ParallaxNexus(this);
    }
}
