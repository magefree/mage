package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SceptreOfEternalGlory extends CardImpl {

    public SceptreOfEternalGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add three mana of any one color. Activate only if you control three or more lands with the same name.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3),
                new TapSourceCost(), SceptreOfEternalGloryCondition.instance
        ));
    }

    private SceptreOfEternalGlory(final SceptreOfEternalGlory card) {
        super(card);
    }

    @Override
    public SceptreOfEternalGlory copy() {
        return new SceptreOfEternalGlory(this);
    }
}

enum SceptreOfEternalGloryCondition implements Condition {
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
