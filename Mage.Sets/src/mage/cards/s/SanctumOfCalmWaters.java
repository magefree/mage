package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author jmharmon
 */
public final class SanctumOfCalmWaters extends CardImpl {

    public SanctumOfCalmWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, you may draw X cards, where X is the number of Shrines you control. If you do, discard a card.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(
                new DrawCardSourceControllerEffect(ShrinesYouControlCount.WHERE_X), true
        ).addHint(ShrinesYouControlCount.getHint());
        ability.addEffect(new DiscardControllerEffect(1).setText("If you do, discard a card"));
        this.addAbility(ability);
    }

    private SanctumOfCalmWaters(final SanctumOfCalmWaters card) {
        super(card);
    }

    @Override
    public SanctumOfCalmWaters copy() {
        return new SanctumOfCalmWaters(this);
    }
}
