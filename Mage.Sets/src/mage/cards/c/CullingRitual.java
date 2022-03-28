package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CullingRitual extends CardImpl {

    public CullingRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // Destroy each nonland permanent with mana value 2 or less. Add {B} or {G} for each permanent destroyed this way.
        this.getSpellAbility().addEffect(new CullingRitualEffect());
    }

    private CullingRitual(final CullingRitual card) {
        super(card);
    }

    @Override
    public CullingRitual copy() {
        return new CullingRitual(this);
    }
}

class CullingRitualEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    CullingRitualEffect() {
        super(Outcome.Benefit);
        staticText = "destroy each nonland permanent with mana value 2 or less. " +
                "Add {B} or {G} for each permanent destroyed this way";
    }

    private CullingRitualEffect(final CullingRitualEffect effect) {
        super(effect);
    }

    @Override
    public CullingRitualEffect copy() {
        return new CullingRitualEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int counter = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            counter += permanent.destroy(source, game, false) ? 1 : 0;
        }
        if (counter == 0) {
            return false;
        }
        int black = player.getAmount(
                0, counter, counter + " permanents were destroyed, " +
                        "choose the amount of black mana to produce (the rest will be green)", game
        );
        Mana mana = new Mana(ManaType.BLACK, black);
        if (black < counter) {
            mana.add(new Mana(ManaType.GREEN, counter - black));
        }
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}
