package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsYouControlCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfCultivation extends CardImpl {

    public TempleOfCultivation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Ojer Kaslem, Deepest Growth.)

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}, {T}: Transform Temple of Cultivation. Activate only if you control ten or more permanents and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new TransformSourceEffect(),
                new ManaCostsImpl("{2}{G}"),
                new TempleOfCultivationCondition(),
                TimingRule.SORCERY
        );
        ability.addCost(new TapSourceCost());
        ability.addHint(new ValueHint("controlled permanents", PermanentsYouControlCount.instance));
        this.addAbility(ability);
    }

    private TempleOfCultivation(final TempleOfCultivation card) {
        super(card);
    }

    @Override
    public TempleOfCultivation copy() {
        return new TempleOfCultivation(this);
    }
}

class TempleOfCultivationCondition extends IntCompareCondition {

    TempleOfCultivationCondition() {
        super(ComparisonType.OR_GREATER, 10);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return PermanentsYouControlCount.instance.calculate(game, source, null);
    }

    @Override
    public String toString() {
        return "if you control ten or more permanents";
    }
}
