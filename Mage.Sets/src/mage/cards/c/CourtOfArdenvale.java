package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Xanderhall
 */
public final class CourtOfArdenvale extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card with mana value 3 or less in your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public CourtOfArdenvale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        
        // When Court of Ardenvale enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, return target permanent card with mana value 3 or less from your graveyard to your hand. If you're the monarch, return that permanent card to the battlefield instead.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), 
                new ReturnFromGraveyardToHandTargetEffect(),
                MonarchIsSourceControllerCondition.instance,
                "return target permanent card with mana value 3 or less from your graveyard to your hand. If you're the monarch, return that permanent card to the battlefield instead."
            ), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addHint(MonarchHint.instance);
        this.addAbility(ability);
    }

    private CourtOfArdenvale(final CourtOfArdenvale card) {
        super(card);
    }

    @Override
    public CourtOfArdenvale copy() {
        return new CourtOfArdenvale(this);
    }
}