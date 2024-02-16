
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Pete Rossi
 */
public final class Purity extends CardImpl {

    public Purity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If noncombat damage would be dealt to you, prevent that damage. You gain life equal to the damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PurityEffect()));

        // When Purity is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private Purity(final Purity card) {
        super(card);
    }

    @Override
    public Purity copy() {
        return new Purity(this);
    }
}

class PurityEffect extends PreventionEffectImpl {

    PurityEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If noncombat damage would be dealt to you, prevent that damage. You gain life equal to the damage prevented this way";
    }

    private PurityEffect(final PurityEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(preventionData.getPreventedDamage(), game, source);
        }

        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return (!(((DamageEvent) event).isCombatDamage()) && event.getTargetId().equals(source.getControllerId()));
        } else {
            return false;
        }
    }

    @Override
    public PurityEffect copy() {
        return new PurityEffect(this);
    }
}
