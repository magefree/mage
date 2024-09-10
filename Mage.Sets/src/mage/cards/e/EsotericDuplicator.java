package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsotericDuplicator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanentThisOrAnother(
            StaticFilters.FILTER_PERMANENT_ARTIFACT,
            true, "{this} or another artifact"
    );

    public EsotericDuplicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.CLUE);

        // Whenever you sacrifice Esoteric Duplicator or another artifact, you may pay {2}. If you do, at the beginning of the next end step, create a token that's a copy of that artifact.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new DoIfCostPaid(new EsotericDuplicatorEffect(), new GenericManaCost(2)), filter
        ));

        // {2}, Sacrifice Esoteric Duplicator: Draw a card.
        this.addAbility(new ClueAbility(true));
    }

    private EsotericDuplicator(final EsotericDuplicator card) {
        super(card);
    }

    @Override
    public EsotericDuplicator copy() {
        return new EsotericDuplicator(this);
    }
}

class EsotericDuplicatorEffect extends OneShotEffect {

    EsotericDuplicatorEffect() {
        super(Outcome.Benefit);
        staticText = "at the beginning of the next end step, create a token that's a copy of that artifact";
    }

    private EsotericDuplicatorEffect(final EsotericDuplicatorEffect effect) {
        super(effect);
    }

    @Override
    public EsotericDuplicatorEffect copy() {
        return new EsotericDuplicatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("sacrificedPermanent");
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new CreateTokenCopyTargetEffect().setSavedPermanent(permanent)
        ), source);
        return true;
    }
}
