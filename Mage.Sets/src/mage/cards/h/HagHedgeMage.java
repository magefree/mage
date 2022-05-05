
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class HagHedgeMage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();
    private static final FilterLandPermanent filter2 = new FilterLandPermanent();

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter2.add(SubType.FOREST.getPredicate());
    }

    private static final String rule = "When {this} enters the battlefield, if you control two or more Swamps, you may have target player discard a card.";
    private static final String rule2 = "When {this} enters the battlefield, if you control two or more Forests, you may put target card from your graveyard on top of your library.";

    public HagHedgeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B/G}");
        this.subtype.add(SubType.HAG);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Hag Hedge-Mage enters the battlefield, if you control two or more Swamps, you may have target player discard a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), true), new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1), rule);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Hag Hedge-Mage enters the battlefield, if you control two or more Forests, you may put target card from your graveyard on top of your library.
        Ability ability2 = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), true), new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 1), rule2);
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
