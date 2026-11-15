package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.common.ScryOrSurveilCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.watchers.common.ScryOrSurveilWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ProctorOfPotential extends CardImpl {

    public ProctorOfPotential(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever this creature or another creature you control enters, surveil 1.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new SurveilEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        ));

        // {W}{U}: Return this card from your graveyard to the battlefield with a finality counter on it. Activate only if you've scried or surveilled this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                CounterType.FINALITY.createInstance(), false
            ),
            new ManaCostsImpl<>("{W}{U}"),
            ScryOrSurveilCondition.instance
        ).addHint(ScryOrSurveilCondition.getHint());
        this.addAbility(ability, new ScryOrSurveilWatcher());
    }

    private ProctorOfPotential(final ProctorOfPotential card) {
        super(card);
    }

    @Override
    public ProctorOfPotential copy() {
        return new ProctorOfPotential(this);
    }
}
