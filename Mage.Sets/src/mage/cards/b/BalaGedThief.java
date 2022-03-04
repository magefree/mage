package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class BalaGedThief extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ALLY, "Allies you control"), null);

    public BalaGedThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE, SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Bala Ged Thief or another Ally enters the battlefield under your control,
        // target player reveals a number of cards from their hand equal to the number of Allies you control.
        // You choose one of them. That player discards that card.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect(TargetController.ANY, xValue), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private BalaGedThief(final BalaGedThief card) {
        super(card);
    }

    @Override
    public BalaGedThief copy() {
        return new BalaGedThief(this);
    }
}
