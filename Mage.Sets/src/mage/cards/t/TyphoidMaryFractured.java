package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.common.ControllerDiscardedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TyphoidMaryFractured extends CardImpl {

    public TyphoidMaryFractured(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Typhoid Mary attacks, choose one at random. If you discarded a card this turn, you choose one instead.
        // * Mary -- Create a Treasure token.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken()))
                .withFirstModeFlavorWord("Mary");
        ability.getModes().setRandom(ControllerDiscardedThisTurnCondition.instance);
        ability.getModes().setChooseText("choose one at random. If you discarded a card this turn, you choose one instead.");

        // * Typhoid Mary -- Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)).withFlavorWord("Typhoid Mary"));

        // * Bloody Mary -- Each opponent loses 2 life and you gain 2 life.
        ability.addMode(new Mode(new LoseLifeOpponentsEffect(2))
                .addEffect(new GainLifeEffect(2).concatBy("and"))
                .withFlavorWord("Bloody Mary"));

        ability.addHint(ControllerDiscardedHint.instance);
        this.addAbility(ability, new DiscardedCardWatcher());
    }

    private TyphoidMaryFractured(final TyphoidMaryFractured card) {
        super(card);
    }

    @Override
    public TyphoidMaryFractured copy() {
        return new TyphoidMaryFractured(this);
    }
}
