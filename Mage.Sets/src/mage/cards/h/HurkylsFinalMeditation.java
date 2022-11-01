package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.effects.common.EndTurnEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HurkylsFinalMeditation extends CardImpl {

    public HurkylsFinalMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}{U}");

        // As long as its not your turn, this spell costs {3} more to cast.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("as long as its not your turn, this spell costs {3} more to cast")
        ));
        this.getSpellAbility().setCostAdjuster(HurkylsFinalMeditationAdjuster.instance);

        // Return all nonland permanents to their owner's hands. End the turn.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
        this.getSpellAbility().addEffect(new EndTurnEffect());
    }

    private HurkylsFinalMeditation(final HurkylsFinalMeditation card) {
        super(card);
    }

    @Override
    public HurkylsFinalMeditation copy() {
        return new HurkylsFinalMeditation(this);
    }
}

enum HurkylsFinalMeditationAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (!game.isActivePlayer(ability.getControllerId())) {
            CardUtil.increaseCost(ability, 3);
        }
    }
}