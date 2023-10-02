
package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author Plopman
 */
public final class EtherswornCanonist extends CardImpl {

    public EtherswornCanonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EtherswornCanonistReplacementEffect()), new EtherswornCanonistWatcher());
    }

    private EtherswornCanonist(final EtherswornCanonist card) {
        super(card);
    }

    @Override
    public EtherswornCanonist copy() {
        return new EtherswornCanonist(this);
    }
}

class EtherswornCanonistWatcher extends Watcher {

    private Set<UUID> castNonartifactSpell = new HashSet<>();

    public EtherswornCanonistWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId() != null) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null) {
                MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
                if (mageObject instanceof Spell) {
                    spell = (Spell) mageObject;
                }
            }
            if (spell != null && !spell.isArtifact(game)) {
                castNonartifactSpell.add(event.getPlayerId());
            }
        }
    }

    @Override
    public void reset() {
        castNonartifactSpell.clear();
    }

    public boolean castNonArtifactSpell(UUID playerId) {
        return castNonartifactSpell.contains(playerId);
    }
}

class EtherswornCanonistReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public EtherswornCanonistReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells";
    }

    private EtherswornCanonistReplacementEffect(final EtherswornCanonistReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EtherswornCanonistReplacementEffect copy() {
        return new EtherswornCanonistReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null && !card.isArtifact(game)) {
            EtherswornCanonistWatcher watcher = game.getState().getWatcher(EtherswornCanonistWatcher.class);
            return watcher != null && watcher.castNonArtifactSpell(event.getPlayerId());
        }
        return false;
    }

}
