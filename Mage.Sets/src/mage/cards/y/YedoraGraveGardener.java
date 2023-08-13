package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomePermanentFacedownEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YedoraGraveGardener extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    public YedoraGraveGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever another nontoken creature you control dies, you may return it to the battlefield face down under its owner's control. It's a Forest land.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new YedoraGraveGardenerEffect(), true, filter, true
        ));
    }

    private YedoraGraveGardener(final YedoraGraveGardener card) {
        super(card);
    }

    @Override
    public YedoraGraveGardener copy() {
        return new YedoraGraveGardener(this);
    }
}

class YedoraGraveGardenerEffect extends OneShotEffect {

    private static final BecomePermanentFacedownEffect.PermanentApplier applier
            = (permanent, game, source) -> {
        permanent.addCardType(game, CardType.LAND);
        permanent.addSubType(game, SubType.FOREST);
        permanent.removeAllAbilities(source.getSourceId(), game);
        permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
    };

    YedoraGraveGardenerEffect() {
        super(Outcome.Benefit);
        staticText = "you may return it to the battlefield face down under its owner's control. " +
                "It's a Forest land. <i>(It has no other types or abilities.)</i>";
    }

    private YedoraGraveGardenerEffect(final YedoraGraveGardenerEffect effect) {
        super(effect);
    }

    @Override
    public YedoraGraveGardenerEffect copy() {
        return new YedoraGraveGardenerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        game.addEffect(new BecomePermanentFacedownEffect(applier, new CardsImpl(card), game), source);
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game,
                false, true, true, null
        );
        return true;
    }
}
