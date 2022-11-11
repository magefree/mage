package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightPaladin extends CardImpl {

    public KnightPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Rapid-fire Battle Cannon -- When Knight Paladin enters the battlefield, it deals 4 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                4, TargetController.OPPONENT, "it"
        )).withFlavorWord("Rapid-fire Battle Cannon"));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private KnightPaladin(final KnightPaladin card) {
        super(card);
    }

    @Override
    public KnightPaladin copy() {
        return new KnightPaladin(this);
    }
}
