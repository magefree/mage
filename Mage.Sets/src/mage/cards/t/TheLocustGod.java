package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TheLocustGodInsectToken;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class TheLocustGod extends CardImpl {

    public TheLocustGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, create a 1/1 blue and red Insect creature token with flying and haste.
        this.addAbility(new DrawCardControllerTriggeredAbility(new CreateTokenEffect(new TheLocustGodInsectToken(), 1, false, false), false));

        // {2}{U}{R}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(new DrawDiscardControllerEffect(1, 1, false), new ManaCostsImpl<>("{2}{U}{R}"));
        this.addAbility(ability);

        // When The Locust God dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandTargetEffect())
        ).setText("return it to its owner's hand at the beginning of the next end step"), false, SetTargetPointer.CARD));
    }

    private TheLocustGod(final TheLocustGod card) {
        super(card);
    }

    @Override
    public TheLocustGod copy() {
        return new TheLocustGod(this);
    }
}
