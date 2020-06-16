package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class SanctumOfCalmWaters extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Shrine");

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    public SanctumOfCalmWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, you may draw X cards, where X is the number of Shrines you control. If you do, discard a card.
        Ability ability = new BeginningOfPreCombatMainTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, true);
        Effect effect = new DiscardControllerEffect(1);
        effect.setText("If you do, discard a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SanctumOfCalmWaters(final SanctumOfCalmWaters card) {
        super(card);
    }

    @Override
    public SanctumOfCalmWaters copy() {
        return new SanctumOfCalmWaters(this);
    }
}
