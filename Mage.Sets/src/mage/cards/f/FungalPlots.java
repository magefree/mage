
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class FungalPlots extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card from your graveyard");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("two Saprolings");

    static {
        filter2.add(SubType.SAPROLING.getPredicate());
    }

    public FungalPlots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // {1}{G}, Exile a creature card from your graveyard: Create a 1/1 green Saproling creature token.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new SaprolingToken()),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);

        // Sacrifice two Saprolings: You gain 2 life and draw a card.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainLifeEffect(2),
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter2, false))
        );
        ability2.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        this.addAbility(ability2);
    }

    private FungalPlots(final FungalPlots card) {
        super(card);
    }

    @Override
    public FungalPlots copy() {
        return new FungalPlots(this);
    }
}
