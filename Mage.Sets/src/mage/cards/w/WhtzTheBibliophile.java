package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MoreThanStartingDeckSizeCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author Grath
 */
public final class WhtzTheBibliophile extends CardImpl {

    public WhtzTheBibliophile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Rulebreaker -- A deck with this commander has no maximum deck size.
        this.addAbility(new RulebreakerAbility(null, false, false));

        // {3}, {T}: You draw a card and gain 1 life. This ability costs {3} less to activate if you had 200 or more
        // cards in your starting deck.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(3));
        ability.addEffect(new GainLifeEffect(1).setText("and gain 1 life"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {3} less to activate if you had 200 or more cards " +
                "in your starting deck."));
        ability.setCostAdjuster(WhtzTheBibliophileAdjuster.instance);
        this.addAbility(ability);
    }

    private WhtzTheBibliophile(final WhtzTheBibliophile card) {
        super(card);
    }

    @Override
    public WhtzTheBibliophile copy() {
        return new WhtzTheBibliophile(this);
    }
}

enum WhtzTheBibliophileAdjuster implements CostAdjuster {
    instance;

    @Override
    public void reduceCost(Ability ability, Game game) {
        // checking state
        if (MoreThanStartingDeckSizeCondition.TWO_HUNDRED.apply(game, ability)) {
            CardUtil.reduceCost(ability, 3);
        }

    }
}