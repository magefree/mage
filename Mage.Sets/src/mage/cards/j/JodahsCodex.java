package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JodahsCodex extends CardImpl {

    public JodahsCodex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Domain - {5}, {T}: Draw a card. This ability costs {1} less to activate for each basic land type among lands you control.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each basic land type among lands you control."));
        ability.setCostAdjuster(JodahsCodexAdjuster.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.addHint(DomainHint.instance);
        this.addAbility(ability);
    }

    private JodahsCodex(final JodahsCodex card) {
        super(card);
    }

    @Override
    public JodahsCodex copy() {
        return new JodahsCodex(this);
    }
}

enum JodahsCodexAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, DomainValue.REGULAR.calculate(game, ability, null));
    }
}
