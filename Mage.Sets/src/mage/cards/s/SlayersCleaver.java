
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SlayersCleaver extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("an Eldrazi");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }
    public SlayersCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1 and must be blocked by an Eldrazi if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1));
        ability.addEffect(new MustBeBlockedByAtLeastOneAttachedEffect(filter).concatBy("and"));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}"), false));
    }

    private SlayersCleaver(final SlayersCleaver card) {
        super(card);
    }

    @Override
    public SlayersCleaver copy() {
        return new SlayersCleaver(this);
    }
}
