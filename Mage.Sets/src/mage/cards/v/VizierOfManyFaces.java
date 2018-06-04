
package mage.cards.v;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.util.functions.ApplyToPermanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class VizierOfManyFaces extends CardImpl {

    public VizierOfManyFaces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Vizier of Many Faces enter the battlefield as a copy of any creature on the battlefield, except if Vizier of Many Faces was embalmed, the token has no mana cost, it's white, and it's a Zombie in addition to its other types.
        Effect effect = new CopyPermanentEffect(new VizierOfManyFacesApplyToPermanent());
        effect.setText("as a copy of any creature on the battlefield, except if {this} was embalmed, the token has no mana cost, it's white, and it's a Zombie in addition to its other types.");
        this.addAbility(new EntersBattlefieldAbility(effect, true), new EmbalmedThisTurnWatcher());

        // Embalm {3}{U}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl("{3}{U}{U}"), this));

    }

    public VizierOfManyFaces(final VizierOfManyFaces card) {
        super(card);
    }

    @Override
    public VizierOfManyFaces copy() {
        return new VizierOfManyFaces(this);
    }
}

class VizierOfManyFacesApplyToPermanent extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        return true;
    }

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        for (Permanent entering : game.getPermanentsEntering().values()) {
            if (entering.getId().equals(copyToObjectId) && entering instanceof PermanentToken) {
                UUID originalCardId = ((PermanentToken) entering).getToken().getCopySourceCard().getId();
                EmbalmedThisTurnWatcher watcher = (EmbalmedThisTurnWatcher) game.getState().getWatchers().get(EmbalmedThisTurnWatcher.class.getSimpleName());
                if (watcher != null) {
                    for (MageObjectReference mor : watcher.getEmbalmedThisTurnCards()) {
                        if (mor.getSourceId().equals(originalCardId) && game.getState().getZoneChangeCounter(originalCardId) == mor.getZoneChangeCounter()) {
                            permanent.getManaCost().clear();
                            if (!permanent.hasSubtype(SubType.ZOMBIE, game)) {
                                permanent.getSubtype(game).add(SubType.ZOMBIE);
                            }
                            permanent.getColor(game).setColor(ObjectColor.WHITE);

                        }
                    }
                }
            }
        }
        return true;
    }

}

class EmbalmedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> embalmedThisTurnTokens;

    public EmbalmedThisTurnWatcher() {
        super(EmbalmedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
        embalmedThisTurnTokens = new HashSet<>();
    }

    public EmbalmedThisTurnWatcher(final EmbalmedThisTurnWatcher watcher) {
        super(watcher);
        embalmedThisTurnTokens = new HashSet<>(watcher.embalmedThisTurnTokens);
    }

    @Override
    public Watcher copy() {
        return new EmbalmedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.EMBALMED_CREATURE) {
            this.embalmedThisTurnTokens.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getEmbalmedThisTurnCards() {
        return this.embalmedThisTurnTokens;
    }

    @Override
    public void reset() {
        super.reset();
        embalmedThisTurnTokens.clear();
    }

}
