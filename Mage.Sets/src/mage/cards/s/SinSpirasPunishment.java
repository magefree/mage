package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author balazskristof
 */
public final class SinSpirasPunishment extends CardImpl {

    public SinSpirasPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LEVIATHAN);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sin enters or attacks, exile a permanent card from your graveyard at random, then create a tapped token that's a copy of that card. If the exiled card is a land card, repeat this process.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new SinSpirasPunishmentEffect()));
    }

    private SinSpirasPunishment(final SinSpirasPunishment card) {
        super(card);
    }

    @Override
    public SinSpirasPunishment copy() {
        return new SinSpirasPunishment(this);
    }
}

class SinSpirasPunishmentEffect extends OneShotEffect {

    SinSpirasPunishmentEffect() {
        super(Outcome.Exile);
        staticText = "exile a permanent card from your graveyard at random, then create a tapped token that's a copy of that card."
            + " If the exiled card is a land card, repeat this process";
    }

    private SinSpirasPunishmentEffect(final SinSpirasPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public SinSpirasPunishmentEffect copy() {
        return new SinSpirasPunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean repeat = true;
        while (repeat) {
            // Exile a permanent card from your graveyard at random
            Card card = RandomUtil.randomFromCollection(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_PERMANENT, game));
            if (card == null) {
                return true;
            }
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            // Then create a tapped token that's a copy of that card
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 1, true, false);
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.apply(game, source);
            game.processAction();
            // If the exiled card is a land card, repeat this process
            repeat = card.getCardType(game).contains(CardType.LAND);
        }
        return true;
    }
}