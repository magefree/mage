package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PinnacleKillShip extends CardImpl {

    public PinnacleKillShip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, it deals 10 damage to up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(10, "it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // 7/7
        this.addAbility(new StationLevelAbility(7)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(7, 7));
    }

    private PinnacleKillShip(final PinnacleKillShip card) {
        super(card);
    }

    @Override
    public PinnacleKillShip copy() {
        return new PinnacleKillShip(this);
    }
}
