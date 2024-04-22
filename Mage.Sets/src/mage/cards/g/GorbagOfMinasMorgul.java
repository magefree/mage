package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GorbagOfMinasMorgul extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a Goblin or Orc you control");

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    public GorbagOfMinasMorgul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Goblin or Orc you control deals combat damage to a player, you may sacrifice it. When you do, choose one--
        // * Draw a card.
        // * Create a Treasure token.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new GorbagOfMinasMorgulEffect(), filter, false,
                SetTargetPointer.PERMANENT, true
        ));
    }

    private GorbagOfMinasMorgul(final GorbagOfMinasMorgul card) {
        super(card);
    }

    @Override
    public GorbagOfMinasMorgul copy() {
        return new GorbagOfMinasMorgul(this);
    }
}

class GorbagOfMinasMorgulEffect extends OneShotEffect {

    private static ReflexiveTriggeredAbility makeAbility() {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        );
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));
        return ability;
    }

    GorbagOfMinasMorgulEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice it. When you do, " + makeAbility().getRule();
    }

    private GorbagOfMinasMorgulEffect(final GorbagOfMinasMorgulEffect effect) {
        super(effect);
    }

    @Override
    public GorbagOfMinasMorgulEffect copy() {
        return new GorbagOfMinasMorgulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null
                && permanent != null
                && player.chooseUse(outcome, "Sacrifice " + permanent.getIdName() + '?', source, game)
                && permanent.sacrifice(source, game)
                && game.fireReflexiveTriggeredAbility(makeAbility(), source) != null;
    }
}
