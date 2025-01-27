package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.MaxSpeedGainAbilityEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RisenNecroregent extends CardImpl {

    public RisenNecroregent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- At the beginning of your end step, create a 2/2 black Zombie creature token.
        this.addAbility(new SimpleStaticAbility(new MaxSpeedGainAbilityEffect(
                new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new ZombieToken()))
        )));
    }

    private RisenNecroregent(final RisenNecroregent card) {
        super(card);
    }

    @Override
    public RisenNecroregent copy() {
        return new RisenNecroregent(this);
    }
}
