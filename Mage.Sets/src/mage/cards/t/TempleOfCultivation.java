package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TempleOfCultivation extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent("you control ten or more permanents"), ComparisonType.MORE_THAN, 9
    );

    public TempleOfCultivation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.nightCard = true;

        // (Transforms from Ojer Kaslem, Deepest Growth.)

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}, {T}: Transform Temple of Cultivation. Activate only if you control ten or more permanents and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{G}"), condition
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(PermanentsYouControlHint.instance));
    }

    private TempleOfCultivation(final TempleOfCultivation card) {
        super(card);
    }

    @Override
    public TempleOfCultivation copy() {
        return new TempleOfCultivation(this);
    }
}
