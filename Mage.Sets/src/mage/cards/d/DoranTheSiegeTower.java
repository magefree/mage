package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * 613.10. Some continuous effects affect game rules rather than objects. For
 * example, effects may modify a player's maximum hand size, or say that a
 * creature must attack this turn if able. These effects are applied after all
 * other continuous effects have been applied. Continuous effects that affect
 * the costs of spells or abilities are applied according to the order specified
 * in rule 601.2e. All other such effects are applied in timestamp order. See
 * also the rules for timestamp order and dependency (rules 613.6 and 613.7)
 *
 * @author LevelX2
 */
public final class DoranTheSiegeTower extends CardImpl {

    public DoranTheSiegeTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Each creature assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, false
        )));
    }

    private DoranTheSiegeTower(final DoranTheSiegeTower card) {
        super(card);
    }

    @Override
    public DoranTheSiegeTower copy() {
        return new DoranTheSiegeTower(this);
    }
}
