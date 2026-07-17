package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfSharedPurpose extends CardImpl {

    public GoShintaiOfSharedPurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your end step, you may pay {1}. If you do, create a 1/1 colorless Spirit creature token for each Shrine you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new SpiritToken(), ShrinesYouControlCount.FOR_EACH), new GenericManaCost(1)
        )).addHint(ShrinesYouControlCount.getHint()));
    }

    private GoShintaiOfSharedPurpose(final GoShintaiOfSharedPurpose card) {
        super(card);
    }

    @Override
    public GoShintaiOfSharedPurpose copy() {
        return new GoShintaiOfSharedPurpose(this);
    }
}
