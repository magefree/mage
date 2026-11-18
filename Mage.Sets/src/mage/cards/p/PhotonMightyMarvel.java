package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class PhotonMightyMarvel extends CardImpl {

    public PhotonMightyMarvel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Photon deals combat damage to a player, add that much mana of any one color. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PhotonMightyMarvelEffect()));
    }

    private PhotonMightyMarvel(final PhotonMightyMarvel card) {
        super(card);
    }

    @Override
    public PhotonMightyMarvel copy() {
        return new PhotonMightyMarvel(this);
    }
}

class PhotonMightyMarvelEffect extends OneShotEffect {

    PhotonMightyMarvelEffect() {
        super(Outcome.Benefit);
        staticText = "add that much mana of any one color. " +
                "Until end of turn, you don't lose this mana as steps and phases end";
    }

    private PhotonMightyMarvelEffect(final PhotonMightyMarvelEffect effect) {
        super(effect);
    }

    @Override
    public PhotonMightyMarvelEffect copy() {
        return new PhotonMightyMarvelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = SavedDamageValue.AMOUNT.calculate(game, source, this);
        if (player == null || amount < 1) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true, "Choose a color of mana to add");
        player.choose(outcome, choice, game);
        player.getManaPool().addMana(choice.getMana(amount), game, source, true);
        return true;
    }
}
