
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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class Lightsaber extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Jedi or Sith");

    static {
        filter.add(Predicates.or(
                SubType.JEDI.getPredicate(),
                SubType.SITH.getPredicate()
        ));
    }

    public Lightsaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has first strike.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike")));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));

        // Equip Jedi or Sith {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(filter)));
    }

    private Lightsaber(final Lightsaber card) {
        super(card);
    }

    @Override
    public Lightsaber copy() {
        return new Lightsaber(this);
    }
}