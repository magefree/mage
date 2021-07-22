
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileSourceUnlessPaysEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class BodySnatcher extends CardImpl {

    public BodySnatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Body Snatcher enters the battlefield, exile it unless you discard a creature card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand(new FilterCreatureCard("a creature card"))))));

        // When Body Snatcher dies, exile Body Snatcher and return target creature card from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("and return target creature card from your graveyard to the battlefield");
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect(), false);
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private BodySnatcher(final BodySnatcher card) {
        super(card);
    }

    @Override
    public BodySnatcher copy() {
        return new BodySnatcher(this);
    }
}
