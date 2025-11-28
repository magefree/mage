package mage.cards.c;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChildOfThePack extends TransformingDoubleFacedCard {

    public ChildOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{R}{G}",
                "Savage Packmate",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "RG");

        // Child of the Pack
        this.getLeftHalfCard().setPT(2, 5);

        // {2}{R}{G}: Create a 2/2 green Wolf creature token.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new WolfToken()), new ManaCostsImpl<>("{2}{R}{G}")
        ));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Savage Packmate
        this.getRightHalfCard().setPT(5, 5);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Other creatures you control get +1/+0.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, true
        )));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private ChildOfThePack(final ChildOfThePack card) {
        super(card);
    }

    @Override
    public ChildOfThePack copy() {
        return new ChildOfThePack(this);
    }
}
