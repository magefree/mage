package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KamiOfJealousThirst extends CardImpl {

    public KamiOfJealousThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {4}{B}: Each opponent loses 2 life and you gain 2 life. This ability costs {4}{B} less to activate if you've drawn three or more cards this turn. Activate only once each turn.
        ActivatedAbility ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{4}{B}")
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.setMaxActivationsPerTurn(1);
        ability.addEffect(new InfoEffect("This ability costs {4}{B} less to activate if you've drawn three or more cards this turn."));
        ability.setCostAdjuster(KamiOfJealousThirstAdjuster.instance);
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private KamiOfJealousThirst(final KamiOfJealousThirst card) {
        super(card);
    }

    @Override
    public KamiOfJealousThirst copy() {
        return new KamiOfJealousThirst(this);
    }
}

enum KamiOfJealousThirstAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int amount = CardsDrawnThisTurnDynamicValue.instance.calculate(game, ability, null);
        if (amount >= 3) {
            CardUtil.adjustCost(ability, new ManaCostsImpl<>("{4}{B}"), false);
        }
    }
}
