
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BehemothSledge extends CardImpl {

    public BehemothSledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}{W}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has trample and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has trample"));
        ability.addEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and lifelink"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private BehemothSledge(BehemothSledge me) {
        super(me);
    }

    @Override
    public BehemothSledge copy() {
        return new BehemothSledge(this);
    }

}
