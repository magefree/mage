package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeepForestHermit extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.SQUIRREL, "Squirrels");

    public DeepForestHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Deep Forest Hermit enters the battlefield, create four 1/1 green Squirrel creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SquirrelToken(), 4)
        ));

        // Squirrels you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));
    }

    private DeepForestHermit(final DeepForestHermit card) {
        super(card);
    }

    @Override
    public DeepForestHermit copy() {
        return new DeepForestHermit(this);
    }
}
