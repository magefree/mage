package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PhaseOutSourceEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSDefiantMirrorMigrant extends CardImpl {

    public USSDefiantMirrorMigrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When U.S.S. Defiant enters, it and up to one target creature you control phase out.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PhaseOutSourceEffect().setText("it"));
        ability.addEffect(new PhaseOutTargetEffect().concatBy("and"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // 5/5
        this.addAbility(new StationLevelAbility(8)
            .withLevelAbility(FlyingAbility.getInstance())
            .withPT(5, 5));
    }

    private USSDefiantMirrorMigrant(final USSDefiantMirrorMigrant card) {
        super(card);
    }

    @Override
    public USSDefiantMirrorMigrant copy() {
        return new USSDefiantMirrorMigrant(this);
    }
}
