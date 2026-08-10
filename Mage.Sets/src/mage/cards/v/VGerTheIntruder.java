package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class VGerTheIntruder extends CardImpl {

    public VGerTheIntruder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever V'Ger enters or attacks, choose one --
        // * Look at target opponent's hand, then draw a card.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new LookAtTargetPlayerHandEffect()
        );
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // * Creatures your opponents control get -1/-0 until your next turn.
        ability.addMode(new Mode(new BoostOpponentsEffect(-1, 0, Duration.UntilYourNextTurn)));
        this.addAbility(ability);
    }

    private VGerTheIntruder(final VGerTheIntruder card) {
        super(card);
    }

    @Override
    public VGerTheIntruder copy() {
        return new VGerTheIntruder(this);
    }
}
