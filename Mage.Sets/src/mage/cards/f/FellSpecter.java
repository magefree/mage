package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class FellSpecter extends CardImpl {

    public FellSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Fell Specter enters the battlefield, target opponent discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DiscardTargetEffect(1)
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever an opponent discards a card, that player loses 2 life.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(
                new LoseLifeTargetEffect(2), false,
                SetTargetPointer.PLAYER
        ));
    }

    private FellSpecter(final FellSpecter card) {
        super(card);
    }

    @Override
    public FellSpecter copy() {
        return new FellSpecter(this);
    }
}
