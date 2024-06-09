package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.abilities.keyword.ConvokeAbility;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashSet;

/**
 * @author notgreat
 */
public enum ConvokedSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;
    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        HashSet<MageObjectReference> set = CardUtil.getSourceCostsTag(game, input.getSource(), ConvokeAbility.convokingCreaturesKey, new HashSet<>(0));
        return set.contains(new MageObjectReference(input.getObject(), game));
    }
}
