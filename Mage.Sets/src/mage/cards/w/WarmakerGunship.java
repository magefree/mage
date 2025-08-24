package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarmakerGunship extends CardImpl {

    public WarmakerGunship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, it deals damage equal to the number of artifacts you control to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(ArtifactYouControlCount.instance)
                .setText("it deals damage equal to the number of artifacts you control to target creature an opponent controls"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // 4/3
        this.addAbility(new StationLevelAbility(6)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(4, 3));
    }

    private WarmakerGunship(final WarmakerGunship card) {
        super(card);
    }

    @Override
    public WarmakerGunship copy() {
        return new WarmakerGunship(this);
    }
}
