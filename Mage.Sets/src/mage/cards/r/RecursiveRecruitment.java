package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CadetToken;
import mage.players.Player;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;

/**
 *
 * @author muz
 */
public final class RecursiveRecruitment extends CardImpl {

    public RecursiveRecruitment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");

        // Create two 2/2 colorless Wizard Soldier creature tokens named Cadet.
        // If this spell was cast from a graveyard,
        // put a +1/+1 counter on each of them for every three cards in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new RecursiveRecruitmentEffect(),
            new CreateTokenEffect(new CadetToken(), 2),
            CastFromGraveyardSourceCondition.instance,
            "create two 2/2 colorless Wizard Soldier creature tokens named Cadet. " +
            "If this spell was cast from a graveyard, put a +1/+1 counter on each of " +
            "them for every three cards in your graveyard"
        ));

        // Flashback {6}{U}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{U}{B}")));
    }

    private RecursiveRecruitment(final RecursiveRecruitment card) {
        super(card);
    }

    @Override
    public RecursiveRecruitment copy() {
        return new RecursiveRecruitment(this);
    }
}

class RecursiveRecruitmentEffect extends OneShotEffect {

    public RecursiveRecruitmentEffect() {
        super(Outcome.Benefit);
    }

    private RecursiveRecruitmentEffect(final RecursiveRecruitmentEffect effect) {
        super(effect);
    }

    @Override
    public RecursiveRecruitmentEffect copy() {
        return new RecursiveRecruitmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        CreateTokenEffect effect = new CreateTokenEffect(new CadetToken(), 2);
        effect.apply(game, source);
        for (UUID tokenId : effect.getLastAddedTokenIds()) {
            Permanent token = game.getPermanent(tokenId);
            if (token != null) {
                int counters = controller.getGraveyard().size() / 3;
                if (counters > 0) {
                    token.addCounters(CounterType.P1P1.createInstance(counters), source, game);
                }
            }
        }
        return true;
    }
}
