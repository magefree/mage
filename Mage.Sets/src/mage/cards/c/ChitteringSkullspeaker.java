package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IntensityAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public final class ChitteringSkullspeaker extends CardImpl {

    public ChitteringSkullspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new IntensityAbility(0));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChitteringSkullspeakerEffect()));
    }

    private ChitteringSkullspeaker(final ChitteringSkullspeaker card) {
        super(card);
    }

    @Override
    public ChitteringSkullspeaker copy() {
        return new ChitteringSkullspeaker(this);
    }
}

class ChitteringSkullspeakerEffect extends OneShotEffect {

    ChitteringSkullspeakerEffect() {
        super(Outcome.Benefit);
        staticText = "cards you own named Chittering Skullspeaker intensify by 1. Then you draw X cards and lose X life, where X is {this}'s intensity";
    }

    private ChitteringSkullspeakerEffect(final ChitteringSkullspeakerEffect effect) {
        super(effect);
    }

    @Override
    public ChitteringSkullspeakerEffect copy() {
        return new ChitteringSkullspeakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = source.getSourcePermanentOrLKI(game);
        if (player == null || sourceCard == null) {
            return false;
        }
        for (Card card : game.getCards()) {
            MageObject object = game.getObject(card.getId());
            if (card.isOwnedBy(player.getId()) && object != null && object.getName().equals("Chittering Skullspeaker")) {
                IntensityAbility.intensify(card, 1, game);
            }
        }
        int intensity = IntensityAbility.getIntensity(sourceCard, game);
        player.drawCards(intensity, source, game);
        player.loseLife(intensity, game, source, false);
        return true;
    }
}
