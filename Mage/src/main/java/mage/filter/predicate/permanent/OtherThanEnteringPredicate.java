package mage.filter.predicate.permanent;

import mage.abilities.Ability;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * Filters out the permanent whose entering triggered the source ability, for the "other" in
 * "Whenever a Mountain you control enters, if you control at least five other Mountains".
 * <p>
 * The source must be an enters trigger that stores "permanentEnteringBattlefield", such as
 * {@link mage.abilities.common.EntersBattlefieldAllTriggeredAbility}.
 *
 * @author notgreat
 */
public enum OtherThanEnteringPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Ability source = input.getSource();
        if (source == null) {
            return true;
        }
        return CardUtil.getEffectValueFromAbility(source, "permanentEnteringBattlefield", Permanent.class)
                .map(entering -> !entering.getId().equals(input.getObject().getId()))
                .orElse(true);
    }

    @Override
    public String toString() {
        return "other than the entering permanent";
    }
}
