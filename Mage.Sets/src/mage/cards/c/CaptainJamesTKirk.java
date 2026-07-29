package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.OfficerToken;

/**
 *
 * @author muz
 */
public final class CaptainJamesTKirk extends CardImpl {

    public CaptainJamesTKirk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Captain Kirk enters or attacks, choose one. If you have no cards in hand, choose one or more instead.
        // * Discard a card, then draw a card.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // * Create a 1/1 red Officer creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new OfficerToken())));

        // * Creatures you control get +1/+0 until end of turn.
        ability.addMode(new Mode(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));

        ability.getModes().setChooseText("choose one. If you have no cards in hand, choose one or more instead.");
        ability.getModes().setMoreCondition(3, HellbentCondition.instance);
        this.addAbility(ability);
    }

    private CaptainJamesTKirk(final CaptainJamesTKirk card) {
        super(card);
    }

    @Override
    public CaptainJamesTKirk copy() {
        return new CaptainJamesTKirk(this);
    }
}
