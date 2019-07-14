
package mage.cards.e;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.ElementalAppealElementalToken;

/**
 *
 * @author North
 */
public final class ElementalAppeal extends CardImpl {

    public ElementalAppeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{R}{R}");

        // Kicker {5}
        this.addAbility(new KickerAbility("{5}"));

        // Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step. If Elemental Appeal was kicked, that creature gets +7/+0 until end of turn.
        this.getSpellAbility().addEffect(new ElementalAppealEffect());
    }

    public ElementalAppeal(final ElementalAppeal card) {
        super(card);
    }

    @Override
    public ElementalAppeal copy() {
        return new ElementalAppeal(this);
    }
}

class ElementalAppealEffect extends OneShotEffect {

    public ElementalAppealEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 7/1 red Elemental creature token with trample and haste. "
                + "Exile it at the beginning of the next end step. "
                + "If this spell was kicked, that creature gets +7/+0 until end of turn";
    }

    public ElementalAppealEffect(final ElementalAppealEffect effect) {
        super(effect);
    }

    @Override
    public ElementalAppealEffect copy() {
        return new ElementalAppealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new ElementalAppealElementalToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            if (KickedCondition.instance.apply(game, source)) {
                List<Predicate<MageObject>> predList = new ArrayList<>();
                for (UUID tokenId : effect.getLastAddedTokenIds()) {
                    predList.add(new CardIdPredicate(tokenId));
                }
                if (!predList.isEmpty()) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent();
                    filter.add(Predicates.or(predList));
                    game.addEffect(new BoostAllEffect(7, 0, Duration.EndOfTurn, filter, false), source);
                }
            }
            return true;
        }
        return false;
    }

}
