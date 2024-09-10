package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CommittedCrimeCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.watchers.common.CommittedCrimeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SeizeTheSecrets extends CardImpl {

    public SeizeTheSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // This spell costs {1} less to cast if you've committed a crime this turn.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(1, CommittedCrimeCondition.instance)
        );
        ability.setRuleAtTheTop(true);
        ability.addHint(CommittedCrimeCondition.getHint());
        this.addAbility(ability, new CommittedCrimeWatcher());

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private SeizeTheSecrets(final SeizeTheSecrets card) {
        super(card);
    }

    @Override
    public SeizeTheSecrets copy() {
        return new SeizeTheSecrets(this);
    }
}
