package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.SourceHasntDealtDamageThisGameCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AssassinMenaceToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;
import mage.watchers.common.DealtDamageThisGameWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ratonhnhaketon extends CardImpl {

    public Ratonhnhaketon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as Ratonhnhaketon hasn't dealt damage yet, it has hexproof and can't be blocked.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                SourceHasntDealtDamageThisGameCondition.instance,
                "as long as {this} hasn't dealt damage yet, it has hexproof"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                SourceHasntDealtDamageThisGameCondition.instance,
                "and can't be blocked"
        ));
        this.addAbility(ability.addHint(SourceHasntDealtDamageThisGameCondition.getHint()), new DealtDamageThisGameWatcher());

        // Whenever Ratonhnhaketon deals combat damage to a player, create a 1/1 black Assassin creature token with menace. When you do, return target Equipment card from your graveyard to the battlefield, then attach it to that token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RatonhnhaketonTokenEffect()));
    }

    private Ratonhnhaketon(final Ratonhnhaketon card) {
        super(card);
    }

    @Override
    public Ratonhnhaketon copy() {
        return new Ratonhnhaketon(this);
    }
}

class RatonhnhaketonTokenEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Equipment card from your graveyard");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    RatonhnhaketonTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 black Assassin creature token with menace. When you do, return " +
                "target Equipment card from your graveyard to the battlefield, then attach it to that token";
    }

    private RatonhnhaketonTokenEffect(final RatonhnhaketonTokenEffect effect) {
        super(effect);
    }

    @Override
    public RatonhnhaketonTokenEffect copy() {
        return new RatonhnhaketonTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new AssassinMenaceToken();
        token.putOntoBattlefield(1, game, source);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new RatonhnhaketonReturnEffect(tokenId), false
            );
            ability.addTarget(new TargetCardInYourGraveyard(filter));
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}

class RatonhnhaketonReturnEffect extends OneShotEffect {

    private final UUID tokenId;

    RatonhnhaketonReturnEffect(UUID tokenId) {
        super(Outcome.Benefit);
        staticText = "return target Equipment card from your graveyard to the battlefield, then attach it to that token";
        this.tokenId = tokenId;
    }

    private RatonhnhaketonReturnEffect(final RatonhnhaketonReturnEffect effect) {
        super(effect);
        this.tokenId = effect.tokenId;
    }

    @Override
    public RatonhnhaketonReturnEffect copy() {
        return new RatonhnhaketonReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        Optional.ofNullable(tokenId)
                .map(game::getPermanent)
                .ifPresent(p -> p.addAttachment(permanent.getId(), source, game));
        return true;
    }
}
