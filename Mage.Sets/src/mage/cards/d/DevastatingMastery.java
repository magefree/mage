package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevastatingMastery extends CardImpl {

    public DevastatingMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{W}{W}");

        // You may pay {2}{W}{W} rather than pay this spell's mana cost.
        Ability costAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{2}{W}{W}"));
        this.addAbility(costAbility);

        // If the {2}{W}{W} cost was paid, an opponent chooses up to two nonland permanents they control and returns them to their owner's hand.
        this.getSpellAbility().addEffect(new DevastatingMasteryAlternativeCostEffect(costAbility.getOriginalId()));

        // Destroy all nonland permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private DevastatingMastery(final DevastatingMastery card) {
        super(card);
    }

    @Override
    public DevastatingMastery copy() {
        return new DevastatingMastery(this);
    }
}

class DevastatingMasteryAlternativeCostEffect extends OneShotEffect {

    private final UUID alternativeCostOriginalID;

    DevastatingMasteryAlternativeCostEffect(UUID alternativeCostOriginalID) {
        super(Outcome.Detriment);
        staticText = "if the {2}{W}{W} cost was paid, an opponent chooses up to two nonland permanents " +
                "they control and returns them to their owner's hand.<br>";
        this.alternativeCostOriginalID = alternativeCostOriginalID;
    }

    private DevastatingMasteryAlternativeCostEffect(DevastatingMasteryAlternativeCostEffect effect) {
        super(effect);
        this.alternativeCostOriginalID = effect.alternativeCostOriginalID;
    }

    @Override
    public DevastatingMasteryAlternativeCostEffect copy() {
        return new DevastatingMasteryAlternativeCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!AlternativeCostSourceAbility.getActivatedStatus(
                game, source, this.alternativeCostOriginalID, false
        )) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (!player.chooseTarget(Outcome.DrawCard, targetOpponent, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 2, StaticFilters.FILTER_PERMANENTS_NON_LAND, true
        );
        opponent.choose(Outcome.ReturnToHand, target, source, game);
        return opponent.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
    }
}
