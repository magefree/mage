package mage.cards.y;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

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
        game.addEffect(new YedoraGraveGardenerContinuousEffect().setTargetPointer(
                new FixedTarget(new MageObjectReference(card, game, 1))
        ), source);
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game,
                false, true, true, null
        );
        return true;
    }
}

class YedoraGraveGardenerContinuousEffect extends ContinuousEffectImpl {

    public YedoraGraveGardenerContinuousEffect() {
        super(Duration.Custom, Layer.CopyEffects_1, SubLayer.FaceDownEffects_1b, Outcome.Neutral);
    }

    public YedoraGraveGardenerContinuousEffect(final YedoraGraveGardenerContinuousEffect effect) {
        super(effect);
    }

    @Override
    public YedoraGraveGardenerContinuousEffect copy() {
        return new YedoraGraveGardenerContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target == null || !target.isFaceDown(game)) {
            discard();
            return false;
        }
        target.removeAllSuperTypes(game);
        target.removeAllCardTypes(game);
        target.removeAllSubTypes(game);
        target.addCardType(game, CardType.LAND);
        target.addSubType(game, SubType.FOREST);
        target.removeAllAbilities(source.getSourceId(), game);
        target.addAbility(new GreenManaAbility(), source.getSourceId(), game);
        return true;
    }
}
