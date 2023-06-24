package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OKagachiMadeManifest extends CardImpl {

    public OKagachiMadeManifest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.nightCard = true;

        // O-Kagachi Made Manifest is all colors.
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.addAbility(new SimpleStaticAbility(new InfoEffect("{this} is all colors")));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever O-Kagachi Made Manifest attacks, defending player chooses a nonland card in your graveyard. Return that card to your hand. O-Kagachi Made Manifest gets +X/+0 until end of turn, where X is the mana value of that card.
        this.addAbility(new AttacksTriggeredAbility(
                new OKagachiMadeManifestEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private OKagachiMadeManifest(final OKagachiMadeManifest card) {
        super(card);
    }

    @Override
    public OKagachiMadeManifest copy() {
        return new OKagachiMadeManifest(this);
    }
}

class OKagachiMadeManifestEffect extends OneShotEffect {

    OKagachiMadeManifestEffect() {
        super(Outcome.Benefit);
        staticText = "defending player chooses a nonland card in your graveyard. Return that card to your hand. " +
                "{this} gets +X/+0 until end of turn, where X is the mana value of that card";
    }

    private OKagachiMadeManifestEffect(final OKagachiMadeManifestEffect effect) {
        super(effect);
    }

    @Override
    public OKagachiMadeManifestEffect copy() {
        return new OKagachiMadeManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card;
        switch (controller.getGraveyard().count(StaticFilters.FILTER_CARD_A_NON_LAND, game)) {
            case 0:
                return false;
            case 1:
                card = controller
                        .getGraveyard()
                        .getCards(StaticFilters.FILTER_CARD_A_NON_LAND, game)
                        .stream()
                        .findFirst()
                        .orElse(null);
                break;
            default:
                TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_A_NON_LAND);
                target.setNotTarget(true);
                player.choose(Outcome.ReturnToHand, controller.getGraveyard(), target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        if (card == null) {
            return false;
        }
        int manaValue = card.getManaValue();
        player.moveCards(card, Zone.HAND, source, game);
        if (manaValue > 0) {
            game.addEffect(new BoostSourceEffect(manaValue, 0, Duration.EndOfTurn), source);
        }
        return true;
    }
}
