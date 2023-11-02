package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.IntComparePredicate;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author fenhl
 */
public final class WildPair extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");

    static {
        // TODO: This should check who cast the spell, not who controls the permanent
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(WildPairCastFromHandPredicate.instance);
    }

    public WildPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Whenever a creature enters the battlefield, if you cast it from your hand, you may search your library for a creature card with the same total power and toughness and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new WildPairEffect(), filter, true, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("Whenever a creature enters the battlefield, if you cast it from your hand, "), new CastFromHandWatcher());
    }

    private WildPair(final WildPair card) {
        super(card);
    }

    @Override
    public WildPair copy() {
        return new WildPair(this);
    }
}

class WildPairEffect extends OneShotEffect {

    WildPairEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search your library for a creature card with the same total power and toughness, put it onto the battlefield, then shuffle";
    }

    private WildPairEffect(final WildPairEffect effect) {
        super(effect);
    }

    @Override
    public WildPairEffect copy() {
        return new WildPairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (controller == null || permanent == null) {
            return false;
        }
        int totalPT = permanent.getPower().getValue() + permanent.getToughness().getValue();
        FilterCreatureCard filter = new FilterCreatureCard("creature card with total power and toughness " + totalPT);
        filter.add(new WildPairPowerToughnessPredicate(totalPT));
        TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
        controller.searchLibrary(target, source, game);
        controller.moveCards(new CardsImpl(
                controller.getLibrary().getCard(target.getFirstTarget(), game)
        ), Zone.BATTLEFIELD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class WildPairPowerToughnessPredicate extends IntComparePredicate<MageObject> {

    WildPairPowerToughnessPredicate(int value) {
        super(ComparisonType.EQUAL_TO, value);
    }

    @Override
    protected int getInputValue(MageObject input) {
        return input.getPower().getValue() + input.getToughness().getValue();
    }
}

enum WildPairCastFromHandPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent permanent, Game game) {
        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        return watcher != null && watcher.spellWasCastFromHand(permanent.getId());
    }
}
