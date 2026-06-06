package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GingerbreadHunter extends AdventureCard {

    public GingerbreadHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT}, "{4}{G}",
                "Puny Snack",
                new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Gingerbread Hunter
        this.getLeftHalfCard().setPT(5, 5);

        // When Gingerbread Hunter enters the battlefield, create a Food token.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Puny Snack
        // Target creature gets -2/-2 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(-2, -2));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        finalizeCard();
    }

    private GingerbreadHunter(final GingerbreadHunter card) {
        super(card);
    }

    @Override
    public GingerbreadHunter copy() {
        return new GingerbreadHunter(this);
    }
}
