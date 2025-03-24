package mage.cards.u;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UginEyeOfTheStorms extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent that's one or more colors");
    private static final FilterSpell filter2 = new FilterSpell("a colorless spell");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
        filter2.add(ColorlessPredicate.instance);
    }

    public UginEyeOfTheStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UGIN);
        this.setStartingLoyalty(7);

        // When you cast this spell, exile up to one target permanent that's one or more colors.
        Ability ability = new CastSourceTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever you cast a colorless spell, exile up to one target permanent that's one or more colors.
        ability = new SpellCastControllerTriggeredAbility(new ExileTargetEffect(), filter2, false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // +2: You gain 3 life and draw a card.
        ability = new LoyaltyAbility(new GainLifeEffect(3), 2);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // 0: Add {C}{C}{C}.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(Mana.ColorlessMana(3)), 0));

        // -11: Search your library for any number of colorless nonland cards, exile them, then shuffle. Until end of turn, you may cast those cards without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new UginEyeOfTheStormsEffect(), -11));
    }

    private UginEyeOfTheStorms(final UginEyeOfTheStorms card) {
        super(card);
    }

    @Override
    public UginEyeOfTheStorms copy() {
        return new UginEyeOfTheStorms(this);
    }
}

class UginEyeOfTheStormsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard("colorless nonland cards");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    UginEyeOfTheStormsEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for any number of colorless nonland cards, exile them, then shuffle. " +
                "Until end of turn, you may cast those cards without paying their mana costs";
    }

    private UginEyeOfTheStormsEffect(final UginEyeOfTheStormsEffect effect) {
        super(effect);
    }

    @Override
    public UginEyeOfTheStormsEffect copy() {
        return new UginEyeOfTheStormsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        if (!cards.isEmpty()) {
            PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                    game, source, cards.getCards(game), TargetController.YOU,
                    Duration.EndOfTurn, true, false, true
            );
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
