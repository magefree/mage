package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyrkulLordOfBones extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public MyrkulLordOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // As long as your life total is less than or equal to half your starting life total, Myrkul, Lord of Bones has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), MyrkulLordOfBonesCondition.instance, "as long as your life total is less than or equal to half your starting life total, {this} has indestructible")));

        // Whenever another nontoken creature you control dies, you may exile it. If you do, create a token that's a copy of that card, except it's an enchantment and loses all other card types.
        this.addAbility(new DiesCreatureTriggeredAbility(new MyrkulLordOfBonesEffect(), true, filter, true));
    }

    private MyrkulLordOfBones(final MyrkulLordOfBones card) {
        super(card);
    }

    @Override
    public MyrkulLordOfBones copy() {
        return new MyrkulLordOfBones(this);
    }
}

enum MyrkulLordOfBonesCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(game.getPlayer(source.getControllerId()))
                .map(Player::getLife)
                .map(x -> (2 * x) <= game.getStartingLife())
                .orElse(false);
    }
}

class MyrkulLordOfBonesEffect extends OneShotEffect {

    MyrkulLordOfBonesEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If you do, create a token that's a copy of that card, " +
                "except it's an enchantment and loses all other card types";
    }

    private MyrkulLordOfBonesEffect(final MyrkulLordOfBonesEffect effect) {
        super(effect);
    }

    @Override
    public MyrkulLordOfBonesEffect copy() {
        return new MyrkulLordOfBonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        return new CreateTokenCopyTargetEffect().setSavedPermanent(
                new PermanentCard(CardUtil.getDefaultCardSideForBattlefield(game, card), source.getControllerId(), game)
        ).setPermanentModifier((token, g) -> {
            token.getCardType().clear();
            token.addCardType(CardType.ENCHANTMENT);
            token.retainAllEnchantmentSubTypes(g);
        }).apply(game, source);
    }
}
