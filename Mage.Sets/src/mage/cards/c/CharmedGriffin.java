package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class CharmedGriffin extends CardImpl {

    public CharmedGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Charmed Griffin enters the battlefield, each other player may put an artifact or enchantment card onto the battlefield from their hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CharmedGriffinEffect(), false));
    }

    private CharmedGriffin(final CharmedGriffin card) {
        super(card);
    }

    @Override
    public CharmedGriffin copy() {
        return new CharmedGriffin(this);
    }
}

class CharmedGriffinEffect extends OneShotEffect {

    public CharmedGriffinEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player may put an artifact or enchantment card onto the battlefield from their hand";
    }

    public CharmedGriffinEffect(final CharmedGriffinEffect effect) {
        super(effect);
    }

    @Override
    public CharmedGriffinEffect copy() {
        return new CharmedGriffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> toBattlefield = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetCardInHand target = new TargetCardInHand(new FilterArtifactOrEnchantmentCard());
                        if (target.canChoose(playerId, source, game)
                                && player.chooseUse(Outcome.Neutral, "Put an artifact or enchantment card from your hand onto the battlefield?", source, game)
                                && player.choose(Outcome.PutCardInPlay, target, source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                toBattlefield.add(card);
                            }
                        }
                    }
                }
            }
            return controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null); // 101.4
        }
        return false;
    }
}
