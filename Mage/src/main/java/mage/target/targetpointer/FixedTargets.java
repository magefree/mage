/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.target.targetpointer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Cards;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class FixedTargets implements TargetPointer {

    final ArrayList<MageObjectReference> targets = new ArrayList<>();
    private boolean initialized;

    public FixedTargets(UUID targetId) {
        targets.add(new MageObjectReference(targetId));
        this.initialized = false;
    }

    public FixedTargets(Cards cards, Game game) {
        for (UUID targetId : cards) {
            MageObjectReference mor = new MageObjectReference(targetId, game);
            targets.add(mor);
        }
        this.initialized = true;
    }

    public FixedTargets(List<Permanent> permanents, Game game) {
        for (Permanent permanent : permanents) {
            MageObjectReference mor = new MageObjectReference(permanent.getId(), permanent.getZoneChangeCounter(game), game);
            targets.add(mor);
        }
        this.initialized = true;
    }

    private FixedTargets(final FixedTargets fixedTargets) {
        this.targets.addAll(fixedTargets.targets);
        this.initialized = fixedTargets.initialized;
    }

    @Override
    public void init(Game game, Ability source) {
        if (!initialized) {
            initialized = true;
            for (MageObjectReference mor : targets) {
                mor.setZoneChangeCounter(game.getState().getZoneChangeCounter(mor.getSourceId()));
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // check target not changed zone
        ArrayList<UUID> list = new ArrayList<>(1);
        for (MageObjectReference mor : targets) {
            if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                list.add(mor.getSourceId());
            }
        }
        return list;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        // check target not changed zone
        for (MageObjectReference mor : targets) {
            if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                return mor.getSourceId();
            }
        }
        return null;
    }

    @Override
    public TargetPointer copy() {
        return new FixedTargets(this);
    }

}
