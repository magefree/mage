package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThievingSprite extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FAERIE, "Faeries you control"), null);

    private static final String rule = "target player reveals X cards from their hand, where X is " +
            xValue.getMessage() + ". You choose one of those cards. That player discards that card";

    public ThievingSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thieving Sprite enters the battlefield, target player reveals X cards from their hand,
        // where X is the number of Faeries you control. You choose one of those cards.
        // That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect(TargetController.ANY, xValue).setText(rule));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private ThievingSprite(final ThievingSprite card) {
        super(card);
    }

    @Override
    public ThievingSprite copy() {
        return new ThievingSprite(this);
    }
}
