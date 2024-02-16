package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinaleOfEternity extends CardImpl {

    public FinaleOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Destroy up to three target creatures with toughness X or less. If X is 10 or more, return all creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setText("destroy up to three target creatures with toughness X or less"));
        this.getSpellAbility().addEffect(new FinaleOfEternityEffect());
        this.getSpellAbility().setTargetAdjuster(FinaleOfEternityAdjuster.instance);
    }

    private FinaleOfEternity(final FinaleOfEternity card) {
        super(card);
    }

    @Override
    public FinaleOfEternity copy() {
        return new FinaleOfEternity(this);
    }
}

enum FinaleOfEternityAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent("creatures with toughness " + xValue + " or less");
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(0, 3, filter, false));
    }
}

class FinaleOfEternityEffect extends OneShotEffect {

    FinaleOfEternityEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "If X is 10 or more, return all creature cards from your graveyard to the battlefield.";
    }

    private FinaleOfEternityEffect(final FinaleOfEternityEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfEternityEffect copy() {
        return new FinaleOfEternityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getManaCostsToPay().getX() < 10) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(
                player.getGraveyard().getCards(
                        StaticFilters.FILTER_CARD_CREATURE, game
                ), Zone.BATTLEFIELD, source, game
        );
    }
}
