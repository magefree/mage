
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.util.functions.AddSubtypeCopyApplier;

/**
 *
 * @author LevelX2
 */
public final class SakashimasStudent extends CardImpl {

    public SakashimasStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ninjutsu {1}{U}
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // You may have Sakashima's Student enter the battlefield as a copy of any creature on the battlefield, except it's still a Ninja in addition to its other creature types.
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new AddSubtypeCopyApplier(SubType.NINJA));
        effect.setText("as a copy of any creature on the battlefield, except it's a Ninja in addition to its other creature types");
        this.addAbility(new EntersBattlefieldAbility(effect, true));

    }

    private SakashimasStudent(final SakashimasStudent card) {
        super(card);
    }

    @Override
    public SakashimasStudent copy() {
        return new SakashimasStudent(this);
    }
}
