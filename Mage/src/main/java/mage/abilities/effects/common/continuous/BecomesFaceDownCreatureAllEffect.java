package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */

public class BecomesFaceDownCreatureAllEffect extends ContinuousEffectImpl {

    protected Map<UUID, Ability> turnFaceUpAbilityMap = new HashMap<>();
    protected FilterPermanent filter;

    public BecomesFaceDownCreatureAllEffect(FilterPermanent filter) {
        super(Duration.EndOfGame, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.BecomeCreature);
        this.filter = filter;
        staticText = "turn all " + filter.getMessage() + " face down. (They're 2/2 creatures.)";
    }

    protected BecomesFaceDownCreatureAllEffect(final BecomesFaceDownCreatureAllEffect effect) {
        super(effect);
        this.turnFaceUpAbilityMap.putAll(effect.turnFaceUpAbilityMap);
        this.filter = effect.filter.copy();
    }

    @Override
    public BecomesFaceDownCreatureAllEffect copy() {
        return new BecomesFaceDownCreatureAllEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        // save permanents to become face down (one time usage on resolve)
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (!perm.isFaceDown(game) && !perm.isTransformable()) {
                affectedObjectList.add(new MageObjectReference(perm, game));
                perm.setFaceDown(true, game);
                // check for Morph
                Card card = game.getCard(perm.getId());
                if (card != null) {
                    for (Ability ability : card.getAbilities(game)) {
                        if (ability instanceof MorphAbility) {
                            this.turnFaceUpAbilityMap.put(card.getId(), new TurnFaceUpAbility(((MorphAbility) ability).getFaceUpCosts()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return affectedObjectList.stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isFaceDown(game))
                .collect(Collectors.toList());
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            BecomesFaceDownCreatureEffect.FaceDownType faceDownType = BecomesFaceDownCreatureEffect.findFaceDownType(game, permanent);
            if (faceDownType != null) {
                BecomesFaceDownCreatureEffect.makeFaceDownObject(game, source.getId(), permanent, faceDownType, null);
            }
        }
        return true;
    }
}
