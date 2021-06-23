package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SpiderToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RotwidowPack extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIDER);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public RotwidowPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {3}{B}{G}, Exile a creature card from your graveyard: Create a 1/2 green Spider creature token with reach, then each opponent loses 1 life for each Spider you control.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new SpiderToken()), new ManaCostsImpl<>("{3}{B}{G}")
        );
        ability.addEffect(new LoseLifeOpponentsEffect(xValue)
                .setText(", then each opponent loses 1 life for each Spider you control.")
        );
        ability.addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        ));
        this.addAbility(ability);
    }

    private RotwidowPack(final RotwidowPack card) {
        super(card);
    }

    @Override
    public RotwidowPack copy() {
        return new RotwidowPack(this);
    }
}
