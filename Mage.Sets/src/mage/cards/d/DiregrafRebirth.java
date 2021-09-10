package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiregrafRebirth extends CardImpl {

    public DiregrafRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}");

        // This spell costs {1} less to cast for each creature that died this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(CreaturesDiedThisTurnCount.instance)
                .setText("this spell costs {1} less to cast for each creature that died this turn")
        ).addHint(CreaturesDiedThisTurnHint.instance).setRuleAtTheTop(true), new CreaturesDiedWatcher());

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Flashback {5}{B}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{5}{B}{G}"), TimingRule.SORCERY));
    }

    private DiregrafRebirth(final DiregrafRebirth card) {
        super(card);
    }

    @Override
    public DiregrafRebirth copy() {
        return new DiregrafRebirth(this);
    }
}
