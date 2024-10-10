package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EndlessAtlas extends CardImpl {

    public EndlessAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}: Draw a card. Activate this ability only if you control three or more lands with the same name.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(2),
                EndlessAtlasCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private EndlessAtlas(final EndlessAtlas card) {
        super(card);
    }

    @Override
    public EndlessAtlas copy() {
        return new EndlessAtlas(this);
    }
}

enum EndlessAtlasCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> lands = new HashSet<>(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        ));
        while (lands.size() >= 3) {
            Permanent land = RandomUtil.randomFromCollection(lands);
            lands.remove(land);
            int amount = 0;
            for (Permanent permanent : lands) {
                if (!permanent.sharesName(land, game)) {
                    continue;
                }
                amount++;
                if (amount >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you control three or more lands with the same name";
    }
}
