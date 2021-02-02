package mage.cards.e;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

/**
 *
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
                new EndlessAtlasCondition()
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

class EndlessAtlasCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Map<String, Integer> landMap = new HashMap<>();
        for (Permanent land : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), game
        )) {
            if (land != null) {
                int landCount = landMap.getOrDefault(land.getName(), 0);
                if (landCount > 1) {
                    return true;
                }
                landMap.put(land.getName(), landCount + 1);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you control three or more lands with the same name";
    }
}
