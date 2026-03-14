package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class SanctumOfStoneFangs extends CardImpl {

    public SanctumOfStoneFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // At the beginning of your precombat main phase, each opponent loses X life and you gain X life, where X is the number of Shrines you control.
        Ability ability = new BeginningOfFirstMainTriggeredAbility(
                new LoseLifeOpponentsEffect(ShrinesYouControlCount.WHERE_X).setText("each opponent loses X life")
        ).addHint(ShrinesYouControlCount.getHint());
        ability.addEffect(new GainLifeEffect(ShrinesYouControlCount.WHERE_X).setText("and you gain X life, where X is the number of Shrines you control"));
        this.addAbility(ability);
    }

    private SanctumOfStoneFangs(final SanctumOfStoneFangs card) {
        super(card);
    }

    @Override
    public SanctumOfStoneFangs copy() {
        return new SanctumOfStoneFangs(this);
    }
}
