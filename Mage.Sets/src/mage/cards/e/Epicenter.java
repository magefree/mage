
package mage.cards.e;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class Epicenter extends CardImpl {

    public Epicenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Target player sacrifices a land.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "Target player"),
                new InvertCondition(new CardsInControllerGraveCondition(7)),
                "Target player sacrifices a land"));
        // Threshold - Each player sacrifices all lands he or she controls instead if seven or more cards are in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new EpicenterEffect(),
                new CardsInControllerGraveCondition(7),
                "<br/><br/><i>Threshold</i> &mdash; Each player sacrifices all lands he or she controls instead if seven or more cards are in your graveyard."));

        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Epicenter(final Epicenter card) {
        super(card);
    }

    @Override
    public Epicenter copy() {
        return new Epicenter(this);
    }
}

class EpicenterEffect extends OneShotEffect {

    EpicenterEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player sacrifices all lands he or she controls";
    }

    EpicenterEffect(final EpicenterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Iterator<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game).iterator();
        while (permanents.hasNext()) {
            Permanent p = permanents.next();
            if (p.isLand()) {
                p.sacrifice(source.getSourceId(), game);
            }
        }

        return true;
    }

    @Override
    public EpicenterEffect copy() {
        return new EpicenterEffect(this);
    }
}
