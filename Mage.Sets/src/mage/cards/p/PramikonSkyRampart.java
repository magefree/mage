package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.PlayerCanOnlyAttackInDirectionRestrictionEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PramikonSkyRampart extends CardImpl {

    public PramikonSkyRampart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As Pramikon, Sky Rampart enters the battlefield, choose left or right.
        this.addAbility(new AsEntersBattlefieldAbility(PlayerCanOnlyAttackInDirectionRestrictionEffect.choiceEffect()));

        // Each player may attack only the nearest opponent in the chosen direction and planeswalkers controlled by that opponent.
        this.addAbility(new SimpleStaticAbility(
                new PlayerCanOnlyAttackInDirectionRestrictionEffect(
                        Duration.WhileOnBattlefield,
                        "the chosen direction"
                )
        ));
    }

    private PramikonSkyRampart(final PramikonSkyRampart card) {
        super(card);
    }

    @Override
    public PramikonSkyRampart copy() {
        return new PramikonSkyRampart(this);
    }
}