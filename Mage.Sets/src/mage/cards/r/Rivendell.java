package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlALegendaryCreatureCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Rivendell extends CardImpl {

    public Rivendell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Rivendell enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlALegendaryCreatureCondition.instance)
                .addHint(YouControlALegendaryCreatureCondition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {1}{U}, {T}: Scry 2. Activate only if you control a legendary creature.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ScryEffect(2, false), new ManaCostsImpl<>("{1}{U}"),
                YouControlALegendaryCreatureCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Rivendell(final Rivendell card) {
        super(card);
    }

    @Override
    public Rivendell copy() {
        return new Rivendell(this);
    }
}
