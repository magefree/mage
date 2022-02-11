
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DirgeOfDread extends CardImpl {

    public DirgeOfDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // All creatures gain fear until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(FearAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_ALL_CREATURES));
        // Cycling {1}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{1}{B}")));
        // When you cycle Dirge of Dread, you may have target creature gain fear until end of turn.
        Ability ability = new CycleTriggeredAbility(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DirgeOfDread(final DirgeOfDread card) {
        super(card);
    }

    @Override
    public DirgeOfDread copy() {
        return new DirgeOfDread(this);
    }
}
