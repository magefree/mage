package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Jmlundeen
 */
public final class TuneUp extends CardImpl {

    public TuneUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");
        

        // Return target artifact card from your graveyard to the battlefield. If it's a Vehicle, it becomes an artifact creature.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new TuneUpEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
    }

    private TuneUp(final TuneUp card) {
        super(card);
    }

    @Override
    public TuneUp copy() {
        return new TuneUp(this);
    }
}

class TuneUpEffect extends OneShotEffect {

    TuneUpEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "If it's a Vehicle, it becomes an artifact creature";
    }

    private TuneUpEffect(final TuneUpEffect effect) {
        super(effect);
    }

    @Override
    public TuneUpEffect copy() {
        return new TuneUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.hasSubtype(SubType.VEHICLE, game)) {
            permanent.addCardType(CardType.CREATURE);
            return true;
        }
        return false;
    }
}
