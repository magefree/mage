package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForumNecroscribe extends CardImpl {

    public ForumNecroscribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ward--Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost(), false));

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, return target creature card from your graveyard to the battlefield.
        Ability ability = new ReparteeAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ForumNecroscribe(final ForumNecroscribe card) {
        super(card);
    }

    @Override
    public ForumNecroscribe copy() {
        return new ForumNecroscribe(this);
    }
}
