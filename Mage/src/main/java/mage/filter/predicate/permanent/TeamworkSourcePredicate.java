package mage.filter.predicate.permanent;

import mage.MageObjectReference;
import mage.abilities.keyword.TeamworkAbility;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashSet;

/**
 *
 * @author muz
 */
public enum TeamworkSourcePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        HashSet<MageObjectReference> set = CardUtil.getSourceCostsTag(
                game, input.getSource(), TeamworkAbility.TEAMWORK_CREATURES_KEY, new HashSet<>()
        );
        return set.contains(new MageObjectReference(input.getObject(), game));
    }
}
