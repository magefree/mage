package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DemonToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ArchfiendsVessel extends CardImpl {

    public ArchfiendsVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Archfiend's Vessel enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, exile it. If you do, create a 5/5 black Demon creature token with flying.
        this.addAbility(new ArchfiendsVesselAbility());
    }

    private ArchfiendsVessel(final ArchfiendsVessel card) {
        super(card);
    }

    @Override
    public ArchfiendsVessel copy() {
        return new ArchfiendsVessel(this);
    }
}

class ArchfiendsVesselAbility extends EntersBattlefieldTriggeredAbility {

    ArchfiendsVesselAbility() {
        super(new ArchfiendsVesselEffect());
    }

    private ArchfiendsVesselAbility(ArchfiendsVesselAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
                EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) event;
                if (entersTheBattlefieldEvent.getTargetId().equals(getSourceId()) && entersTheBattlefieldEvent.getFromZone() == Zone.GRAVEYARD) {
                    return true;
                } else {
                    SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
                    List<Spell> spellsCastFromGraveyard = watcher.getSpellsCastFromGraveyardThisTurn(getControllerId());
                    if (spellsCastFromGraveyard != null) {
                        return spellsCastFromGraveyard.stream()
                            .anyMatch(spell -> spell.getMainCard().getId().equals((entersTheBattlefieldEvent.getTarget().getMainCard().getId())));
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ArchfiendsVesselAbility copy() {
        return new ArchfiendsVesselAbility(this);
    }
}

class ArchfiendsVesselEffect extends OneShotEffect {

    ArchfiendsVesselEffect() {
        super(Outcome.Benefit);
        staticText = "if it entered from your graveyard or you cast it from your graveyard, exile it. If you do, create a 5/5 black Demon creature token with flying";
    }

    private ArchfiendsVesselEffect(ArchfiendsVesselEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent archfiendsVessel = source.getSourcePermanentIfItStillExists(game);
        if (archfiendsVessel != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                boolean moved = controller.moveCards(archfiendsVessel, Zone.EXILED, source, game);
                if (moved) {
                    Token token = new DemonToken();
                    token.putOntoBattlefield(1, game, source, controller.getId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ArchfiendsVesselEffect copy() {
        return new ArchfiendsVesselEffect(this);
    }
}
