package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public final class StuffyDoll extends CardImpl {

    public StuffyDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // As Stuffy Doll enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Damage)));
        // Stuffy Doll is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // Whenever Stuffy Doll is dealt damage, it deals that much damage to the chosen player.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new StuffyDollEffect(), false));
        // {T}: Stuffy Doll deals 1 damage to itself.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageSelfEffect(1), new TapSourceCost()));
    }

    private StuffyDoll(final StuffyDoll card) {
        super(card);
    }

    @Override
    public StuffyDoll copy() {
        return new StuffyDoll(this);
    }
}

class StuffyDollEffect extends OneShotEffect {

    StuffyDollEffect() {
        super(Outcome.GainLife);
        staticText = "it deals that much damage to the chosen player";
    }

    private StuffyDollEffect(final StuffyDollEffect effect) {
        super(effect);
    }

    @Override
    public StuffyDollEffect copy() {
        return new StuffyDollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        Player player = game.getPlayer(playerId);
        if (player != null && player.canRespond()) {
            player.damage((Integer) this.getValue("damage"), source.getSourceId(), source, game);
        }
        return true;
    }
}
