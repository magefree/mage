package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSDefiantToughLittleShip extends CardImpl {

    public USSDefiantToughLittleShip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When U.S.S. Defiant enters, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // Station
        this.addAbility(new StationAbility());

        // STATION 5+
        // Flying
        // Hexproof
        // 7/4
        this.addAbility(new StationLevelAbility(5)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(HexproofAbility.getInstance())
            .withPT(7, 4));
    }

    private USSDefiantToughLittleShip(final USSDefiantToughLittleShip card) {
        super(card);
    }

    @Override
    public USSDefiantToughLittleShip copy() {
        return new USSDefiantToughLittleShip(this);
    }
}
