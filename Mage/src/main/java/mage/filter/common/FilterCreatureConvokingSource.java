
package mage.filter.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.keyword.ConvokeAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public class FilterCreatureConvokingSource extends FilterCreaturePermanent {


    public FilterCreatureConvokingSource() {
        this("creature that convoked it");
    }

    public FilterCreatureConvokingSource(String name) {
        super(name);
    }

    public FilterCreatureConvokingSource(final FilterCreatureConvokingSource filter) {
        super(filter);
    }

    @Override
    public FilterCreatureConvokingSource copy() {
        return new FilterCreatureConvokingSource(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (!super.match(permanent, playerId, source, game)) {return false;}
        Set<MageObjectReference> convokeSet = (Set<MageObjectReference>) CardUtil.getSourceCostTags(game, source)
                .get(ConvokeAbility.convokingCreaturesKey);
        return (convokeSet != null && convokeSet.contains(new MageObjectReference(permanent, game)));
    }
}