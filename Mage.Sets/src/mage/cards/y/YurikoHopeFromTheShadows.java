package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class YurikoHopeFromTheShadows extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(new CardsInControllerGraveyardCount());

    public YurikoHopeFromTheShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Yuriko enters, choose one --
        // * Target creature gets -X/-0 until end of turn, where X is the number of cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn)
                .setText("target creature gets -X/-0 until end of turn, where X is the number of cards in your graveyard")
        );
        ability.addTarget(new TargetCreaturePermanent());

        // * Surveil 2.
        ability.addMode(new Mode(new SurveilEffect(2)));

        this.addAbility(ability);
    }

    private YurikoHopeFromTheShadows(final YurikoHopeFromTheShadows card) {
        super(card);
    }

    @Override
    public YurikoHopeFromTheShadows copy() {
        return new YurikoHopeFromTheShadows(this);
    }
}
