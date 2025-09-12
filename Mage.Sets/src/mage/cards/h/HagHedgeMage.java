
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HagHedgeMage extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.SWAMP, "you control two or more Swamps"),
            ComparisonType.MORE_THAN, 1
    );
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.FOREST, "you control two or more Forests"),
            ComparisonType.MORE_THAN, 1
    );

    public HagHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}");
        this.subtype.add(SubType.HAG);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Hag Hedge-Mage enters the battlefield, if you control two or more Swamps, you may have target player discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), true).withInterveningIf(condition);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Hag Hedge-Mage enters the battlefield, if you control two or more Forests, you may put target card from your graveyard on top of your library.
        Ability ability2 = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), true).withInterveningIf(condition2);
        ability2.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability2);
    }

    private HagHedgeMage(final HagHedgeMage card) {
        super(card);
    }

    @Override
    public HagHedgeMage copy() {
        return new HagHedgeMage(this);
    }
}
