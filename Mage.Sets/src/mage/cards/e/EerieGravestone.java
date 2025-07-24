package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EerieGravestone extends CardImpl {

    public EerieGravestone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When this artifact enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {1}{B}, Sacrifice this artifact: Mill four cards. You may put a creature card from among them into your hand.
        Ability ability = new SimpleActivatedAbility(
                new MillThenPutInHandEffect(4, StaticFilters.FILTER_CARD_CREATURE), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EerieGravestone(final EerieGravestone card) {
        super(card);
    }

    @Override
    public EerieGravestone copy() {
        return new EerieGravestone(this);
    }
}
