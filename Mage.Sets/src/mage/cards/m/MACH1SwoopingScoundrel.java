package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MACH1SwoopingScoundrel extends CardImpl {

    public MACH1SwoopingScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When MACH-1 enters and whenever you gain life, surveil 1. This ability triggers only once each turn.
        this.addAbility(new OrTriggeredAbility(
            Zone.BATTLEFIELD,
            new SurveilEffect(1),
            new EntersBattlefieldTriggeredAbility(null),
            new GainLifeControllerTriggeredAbility(null)
        ).setTriggersLimitEachTurn(1));
    }

    private MACH1SwoopingScoundrel(final MACH1SwoopingScoundrel card) {
        super(card);
    }

    @Override
    public MACH1SwoopingScoundrel copy() {
        return new MACH1SwoopingScoundrel(this);
    }
}
