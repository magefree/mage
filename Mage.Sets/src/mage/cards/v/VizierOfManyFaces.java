package mage.cards.v;

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
import mage.util.functions.CopyApplier;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
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
        Effect effect = new CopyPermanentEffect(new VizierOfManyFacesCopyApplier());
        effect.setText("as a copy of any creature on the battlefield, except if {this} was embalmed, the token has no mana cost, it's white, and it's a Zombie in addition to its other types.");
        this.addAbility(new EntersBattlefieldAbility(effect, true), new EmbalmedThisTurnWatcher());

        // Embalm {3}{U}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{U}{U}"), this));
    }

    private VizierOfManyFaces(final VizierOfManyFaces card) {
        super(card);
    }

    @Override
    public VizierOfManyFaces copy() {
        return new VizierOfManyFaces(this);
    }
}

class VizierOfManyFacesCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        for (Permanent entering : game.getPermanentsEntering().values()) {
            if (!entering.getId().equals(copyToObjectId) || !(entering instanceof PermanentToken)) {
                continue;
            }
            UUID originalCardId = ((PermanentToken) entering).getToken().getCopySourceCard().getId();
            EmbalmedThisTurnWatcher watcher = game.getState().getWatcher(EmbalmedThisTurnWatcher.class);
            if (watcher == null) {
                continue;
            }
            for (MageObjectReference mor : watcher.getEmbalmedThisTurnCards()) {
                if (!Objects.equals(mor.getSourceId(), originalCardId) || game.getState().getZoneChangeCounter(originalCardId) != mor.getZoneChangeCounter()) {
                    continue;
                }
                blueprint.getManaCost().clear();
                if (!blueprint.getSubtype().contains(SubType.ZOMBIE)) {
                    blueprint.getSubtype().add(SubType.ZOMBIE);
                }
                blueprint.getColor().setColor(ObjectColor.WHITE);
            }
        }
        return true;
    }
}

class EmbalmedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> embalmedThisTurnTokens;

    public EmbalmedThisTurnWatcher() {
        super(WatcherScope.GAME);
        embalmedThisTurnTokens = new HashSet<>();
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
