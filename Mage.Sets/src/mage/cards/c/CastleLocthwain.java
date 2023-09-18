package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastleLocthwain extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public CastleLocthwain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Castle Locthwain enters the battlefield tapped unless you control a Swamp.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new TapSourceEffect(), condition
        ), "tapped unless you control a Swamp"));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {1}{B}{B}, {T}: Draw a card, then you lose life equal to the number of cards in your hand.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1).setText("draw a card,"), new ManaCostsImpl<>("{1}{B}{B}")
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(CardsInControllerHandCount.instance)
                .setText("then you lose life equal to the number of cards in your hand"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleLocthwain(final CastleLocthwain card) {
        super(card);
    }

    @Override
    public CastleLocthwain copy() {
        return new CastleLocthwain(this);
    }
}
