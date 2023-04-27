package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolKanarTheTainted extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("other target creature or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SolKanarTheTainted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your end step, choose one that hasn't been chosen --
        // * Draw a card.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        );
        ability.getModes().setEachModeOnlyOnce(true);

        // * Each opponent loses 2 life and you gain 2 life.
        ability.addMode(new Mode(new LoseLifeOpponentsEffect(2))
                .addEffect(new GainLifeEffect(2).concatBy("and")));

        // * Sol'Kanar the Tainted deals 3 damage to up to one other target creature or planeswalker.
        ability.addMode(new Mode(new DamageTargetEffect(3))
                .addTarget(new TargetPermanent(0, 1, filter)));

        // * Exile Sol'Kanar, then return it to the battlefield under an opponent's control.
        ability.addMode(new Mode(new SolKanarTheTaintedEffect()));
        this.addAbility(ability);
    }

    private SolKanarTheTainted(final SolKanarTheTainted card) {
        super(card);
    }

    @Override
    public SolKanarTheTainted copy() {
        return new SolKanarTheTainted(this);
    }
}

class SolKanarTheTaintedEffect extends OneShotEffect {

    SolKanarTheTaintedEffect() {
        super(Outcome.Benefit);
        staticText = "exile {this}, then return it to the battlefield under an opponent's control";
    }

    private SolKanarTheTaintedEffect(final SolKanarTheTaintedEffect effect) {
        super(effect);
    }

    @Override
    public SolKanarTheTaintedEffect copy() {
        return new SolKanarTheTaintedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card instanceof PermanentToken) {
            return true;
        }
        TargetOpponent target = new TargetOpponent(true);
        player.choose(outcome, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        return opponent == null || opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
