package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;


/**
 * @author TheElk801
 */
public final class TyvarJubilantBrawler extends CardImpl {

    public TyvarJubilantBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TYVAR);
        this.setStartingLoyalty(3);

        // You may activate abilities of creatures you control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(new TyvarJubilantBrawlerHasteEffect()));

        // +1: Untap up to one target creature.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Mill three cards, then you may return a creature card with mana value 2 or less from your graveyard to the battlefield.
        ability = new LoyaltyAbility(new MillCardsControllerEffect(3), -2);
        ability.addEffect(new TyvarJubilantBrawlerReturnEffect());
        this.addAbility(ability);
    }

    private TyvarJubilantBrawler(final TyvarJubilantBrawler card) {
        super(card);
    }

    @Override
    public TyvarJubilantBrawler copy() {
        return new TyvarJubilantBrawler(this);
    }
}

class TyvarJubilantBrawlerHasteEffect extends AsThoughEffectImpl {

    public TyvarJubilantBrawlerHasteEffect() {
        super(AsThoughEffectType.ACTIVATE_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may activate abilities of creatures you control as though those creatures had haste";
    }

    public TyvarJubilantBrawlerHasteEffect(final TyvarJubilantBrawlerHasteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TyvarJubilantBrawlerHasteEffect copy() {
        return new TyvarJubilantBrawlerHasteEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId());
    }
}

class TyvarJubilantBrawlerReturnEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    TyvarJubilantBrawlerReturnEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may return a creature card " +
                "with mana value 2 or less from your graveyard to the battlefield";
    }

    private TyvarJubilantBrawlerReturnEffect(final TyvarJubilantBrawlerReturnEffect effect) {
        super(effect);
    }

    @Override
    public TyvarJubilantBrawlerReturnEffect copy() {
        return new TyvarJubilantBrawlerReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(0, 1, filter);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
