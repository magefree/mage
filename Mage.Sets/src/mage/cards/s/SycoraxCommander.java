package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FaceVillainousChoiceOpponentsEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SycoraxCommander extends CardImpl {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Discard, new SycoraxCommanderFirstChoice(), new SycoraxCommanderSecondChoice()
    );

    public SycoraxCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Sanctified Rules of Combat -- When Sycorax Commander enters the battlefield, each opponent faces a villainous choice -- That opponent discards all the cards in their hand, then draws that many cards minus one, or Sycorax Commander deals damage to that player equal to the number of cards in their hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new FaceVillainousChoiceOpponentsEffect(choice)
        ).withFlavorWord("Sanctified Rules of Combat"));
    }

    private SycoraxCommander(final SycoraxCommander card) {
        super(card);
    }

    @Override
    public SycoraxCommander copy() {
        return new SycoraxCommander(this);
    }
}

class SycoraxCommanderFirstChoice extends VillainousChoice {

    SycoraxCommanderFirstChoice() {
        super(
                "That opponent discards all the cards in their hand, then draws that many cards minus one",
                "Discard your hand and draw one less card"
        );
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        int discarded = player.discard(player.getHand(), false, source, game).size();
        if (discarded > 0) {
            player.drawCards(discarded - 1, source, game);
        }
        return true;
    }
}

class SycoraxCommanderSecondChoice extends VillainousChoice {

    SycoraxCommanderSecondChoice() {
        super(
                "{this} deals damage to that player equal to the number of cards in their hand",
                "Take damage equal to the number of cards in your hand"
        );
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return player.damage(player.getHand().size(), source, game) > 0;
    }
}
