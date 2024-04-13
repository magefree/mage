package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaughingJasperFlint extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public LaughingJasperFlint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Creatures you control but don't own are Mercenaries in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new AddCardSubtypeAllEffect(
                filter, SubType.MERCENARY, DependencyType.AddingCreatureType
        )));

        // At the beginning of your upkeep, exile the top X cards of target opponent's library, where X is the number of outlaws you control. Until end of turn, you may cast spells from among those cards, and mana of any type can be spent to cast those spells.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LaughingJasperFlintEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(LaughingJasperFlintEffect.getHint()));
    }

    private LaughingJasperFlint(final LaughingJasperFlint card) {
        super(card);
    }

    @Override
    public LaughingJasperFlint copy() {
        return new LaughingJasperFlint(this);
    }
}

class LaughingJasperFlintEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(OutlawPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Outlaws you control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    LaughingJasperFlintEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of target opponent's library, " +
                "where X is the number of outlaws you control. Until end of turn, " +
                "you may cast spells from among those cards, and mana of any type can be spent to cast those spells";
    }

    private LaughingJasperFlintEffect(final LaughingJasperFlintEffect effect) {
        super(effect);
    }

    @Override
    public LaughingJasperFlintEffect copy() {
        return new LaughingJasperFlintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        int count = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, count));
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
        }
        return true;
    }
}
