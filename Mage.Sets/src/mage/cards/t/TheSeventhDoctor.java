package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSeventhDoctor extends CardImpl {

    public TheSeventhDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Whenever The Seventh Doctor attacks, choose a card in your hand. Defending player guesses whether that card's mana value is greater than the number of artifacts you control. If they guessed wrong, you may cast it without paying its mana cost. If you don't cast a spell this way, investigate.
        this.addAbility(new AttacksTriggeredAbility(
                new TheSeventhDoctorEffect(), false, null, SetTargetPointer.PLAYER
        ).addHint(ArtifactYouControlHint.instance));
    }

    private TheSeventhDoctor(final TheSeventhDoctor card) {
        super(card);
    }

    @Override
    public TheSeventhDoctor copy() {
        return new TheSeventhDoctor(this);
    }
}

class TheSeventhDoctorEffect extends OneShotEffect {

    TheSeventhDoctorEffect() {
        super(Outcome.Benefit);
        staticText = "choose a card in your hand. Defending player guesses whether that card's mana value " +
                "is greater than the number of artifacts you control. If they guessed wrong, " +
                "you may cast it without paying its mana cost. If you don't cast a spell this way, investigate";
    }

    private TheSeventhDoctorEffect(final TheSeventhDoctorEffect effect) {
        super(effect);
    }

    @Override
    public TheSeventhDoctorEffect copy() {
        return new TheSeventhDoctorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.getHand().isEmpty()) {
            InvestigateEffect.doInvestigate(source.getControllerId(), 1, game, source);
            return true;
        }
        TargetCard target = new TargetCardInHand();
        controller.choose(Outcome.PlayForFree, controller.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        game.informPlayers(controller.getLogName() + " has chosen a card in their hand");
        Player defender = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (defender == null || card == null) {
            InvestigateEffect.doInvestigate(source.getControllerId(), 1, game, source);
            return true;
        }
        int count = ArtifactYouControlCount.instance.calculate(game, source, this);
        boolean guessedGreater = defender.chooseUse(
                outcome, "Is the chosen card's mana value greater than the number of artifacts " +
                        controller.getName() + " controls?", controller.getName() + " controls " +
                        count + " artifacts", "Greater than", "Not greater than", source, game
        );
        game.informPlayers(defender.getLogName() +
                " has guessed that the chosen card's mana value is " + (guessedGreater ? "" : "not ") +
                "greater than the number of artifacts controlled by " + controller.getLogName());
        if (card.getManaValue() > count == guessedGreater
                || !CardUtil.castSpellWithAttributesForFree(controller, source, game, card)) {
            InvestigateEffect.doInvestigate(source.getControllerId(), 1, game, source);
        }
        return true;
    }
}
