
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class SwansOfBrynArgoll extends CardImpl {

    public SwansOfBrynArgoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W/U}{W/U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a source would deal damage to Swans of Bryn Argoll, prevent that damage. The source's controller draws cards equal to the damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SwansOfBrynArgollEffect()));

    }

    private SwansOfBrynArgoll(final SwansOfBrynArgoll card) {
        super(card);
    }

    @Override
    public SwansOfBrynArgoll copy() {
        return new SwansOfBrynArgoll(this);
    }
}

class SwansOfBrynArgollEffect extends PreventionEffectImpl {

    SwansOfBrynArgollEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If a source would deal damage to {this}, prevent that damage. The source's controller draws cards equal to the damage prevented this way";
    }

    private SwansOfBrynArgollEffect(final SwansOfBrynArgollEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            boolean passed = false;
            MageObject sourceOfDamage = game.getObject(event.getSourceId());
            if (sourceOfDamage != null) {
                Spell spell = game.getStack().getSpell(sourceOfDamage.getId());
                Permanent permanent = game.getPermanentOrLKIBattlefield(sourceOfDamage.getId());
                CommandObject emblem = (CommandObject)game.getEmblem(sourceOfDamage.getId());
                if (spell != null) {
                    Player controllerOfSpell = game.getPlayer(spell.getControllerId());
                    if(controllerOfSpell != null) {
                        // original event is not a draw event, so skip it in params
                        controllerOfSpell.drawCards(preventionEffectData.getPreventedDamage(), source, game);
                        passed = true;
                    }
                }
                if (permanent != null) {
                    Player controllerOfPermanent = game.getPlayer(permanent.getControllerId());
                    if(controllerOfPermanent != null) {
                        // original event is not a draw event, so skip it in params
                        controllerOfPermanent.drawCards(preventionEffectData.getPreventedDamage(), source, game);
                        passed = true;
                    }
                }
                if (emblem != null) {
                    Player controllerOfEmblem = game.getPlayer(emblem.getControllerId());
                    if(controllerOfEmblem != null) {
                        // original event is not a draw event, so skip it in params
                        controllerOfEmblem.drawCards(preventionEffectData.getPreventedDamage(), source, game);
                    }
                    passed = true;
                }
                if (!passed) {
                    // Needed for cards that do damage from hand e.g. Gempalm Incinerator
                    Card cardSource = game.getCard(event.getSourceId());
                    if (cardSource != null) {
                        Player owner = game.getPlayer(cardSource.getOwnerId());
                        if (owner != null) {
                            // original event is not a draw event, so skip it in params
                            owner.drawCards(preventionEffectData.getPreventedDamage(), source, game);
                        }
                    }
                }
            }
        }
        return preventionEffectData.isReplaced();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public SwansOfBrynArgollEffect copy() {
        return new SwansOfBrynArgollEffect(this);
    }
}
