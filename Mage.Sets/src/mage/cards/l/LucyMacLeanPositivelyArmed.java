package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LucyMacLeanPositivelyArmed extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player other than that token's controller");

    static {
        filter.add(LucyMacLeanPositivelyArmedPredicate.instance);
    }

    public LucyMacLeanPositivelyArmed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Golden Rule -- Whenever a token enters, you may have target player other than its controller create a token that's a copy of it, then you draw a card if an opponent created a token this way. Do this only once each turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new LucyMacLeanPositivelyArmedEffect(), StaticFilters.FILTER_PERMANENT_TOKEN, true
        ).setDoOnlyOnceEachTurn(true);
        ability.addTarget(new TargetPlayer(filter));
        this.addAbility(ability.withFlavorWord("Golden Rule"));
    }

    private LucyMacLeanPositivelyArmed(final LucyMacLeanPositivelyArmed card) {
        super(card);
    }

    @Override
    public LucyMacLeanPositivelyArmed copy() {
        return new LucyMacLeanPositivelyArmed(this);
    }
}

enum LucyMacLeanPositivelyArmedPredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        return CardUtil.getEffectValueFromAbility(input.getSource(), "permanentEnteringBattlefield", Permanent.class)
                .filter(permanent -> permanent.isControlledBy(input.getPlayerId()))
                .isPresent();
    }
}

class LucyMacLeanPositivelyArmedEffect extends OneShotEffect {

    LucyMacLeanPositivelyArmedEffect() {
        super(Outcome.Benefit);
        staticText = "you may have target player other than its controller create a token that's a copy of it, " +
                "then you draw a card if an opponent created a token this way";
    }

    private LucyMacLeanPositivelyArmedEffect(final LucyMacLeanPositivelyArmedEffect effect) {
        super(effect);
    }

    @Override
    public LucyMacLeanPositivelyArmedEffect copy() {
        return new LucyMacLeanPositivelyArmedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (player == null || permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(player.getId());
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        if (effect.getAddedPermanents().isEmpty()) {
            return false;
        }
        if (game.getOpponents(source.getControllerId()).contains(player.getId())) {
            Optional.of(source)
                    .map(Controllable::getControllerId)
                    .map(game::getPlayer)
                    .ifPresent(controller -> controller.drawCards(1, source, game));
        }
        return true;
    }
}
