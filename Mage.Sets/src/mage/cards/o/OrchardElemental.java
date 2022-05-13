package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
 */
public final class OrchardElemental extends CardImpl {

    public OrchardElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Council's dilemma</i> &mdash When Orchard Elemental enters the battlefield, starting with you, each player votes for sprout or harvest. Put two +1/+1 counters on Orchard Elemental for each sprout vote. You gain 3 life for each harvest vote.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new OrchardElementalEffect(), false)
                .setAbilityWord(AbilityWord.COUNCILS_DILEMMA)
        );
    }

    private OrchardElemental(final OrchardElemental card) {
        super(card);
    }

    @Override
    public OrchardElemental copy() {
        return new OrchardElemental(this);
    }
}

class OrchardElementalEffect extends OneShotEffect {

    OrchardElementalEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for sprout or harvest. " +
                "Put two +1/+1 counters on {this} for each sprout vote. You gain 3 life for each harvest vote";
    }

    private OrchardElementalEffect(final OrchardElementalEffect effect) {
        super(effect);
    }

    @Override
    public OrchardElementalEffect copy() {
        return new OrchardElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Outcome.Benefit - AI will boost all the time (Sprout choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Sprout (two +1/+1 counters)", "Harvest (3 life)", Outcome.Benefit);
        vote.doVotes(source, game);

        int sproutCount = vote.getVoteCount(true);
        int harvestCount = vote.getVoteCount(false);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (sproutCount > 0 && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(2 * sproutCount), source.getControllerId(), source, game);
        }

        Player player = game.getPlayer(source.getControllerId());
        if (harvestCount > 0 && player != null) {
            player.gainLife(3 * harvestCount, game, source);
        }

        return sproutCount + harvestCount > 0;
    }
}
