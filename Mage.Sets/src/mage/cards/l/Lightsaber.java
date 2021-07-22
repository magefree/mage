
package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class Lightsaber extends CardImpl {

    public Lightsaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equiped creature gets +1/+0 and has firsttrike
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike")));

        // Equip 3
        Ability ability = new EquipAbility(3);
        ability.setCostAdjuster(LightsaberAdjuster.instance);
        this.addAbility(ability);

        // Lightsaber's equip ability costs {1} if it targets a Jedi or Sith.
        this.addAbility(new SimpleStaticAbility(new InfoEffect("{this}'s equip ability costs {1} if it targets a Jedi or Sith")));
    }

    private Lightsaber(final Lightsaber card) {
        super(card);
    }

    @Override
    public Lightsaber copy() {
        return new Lightsaber(this);
    }
}

enum LightsaberAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            if (CardUtil
                    .getAllPossibleTargets(ability, game)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(permanent -> permanent.hasSubtype(SubType.SITH, game)
                            || permanent.hasSubtype(SubType.JEDI, game))) {
                return;
            }
        } else {
            Permanent permanent = game.getPermanent(ability.getFirstTarget());
            if (permanent == null || !(permanent.hasSubtype(SubType.SITH, game)
                    || permanent.hasSubtype(SubType.JEDI, game))) {
                return;
            }
        }
        ability.getCosts().clear();
        ability.addCost(new GenericManaCost(1));
    }
}