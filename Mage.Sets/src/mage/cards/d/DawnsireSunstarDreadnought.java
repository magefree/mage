package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnsireSunstarDreadnought extends CardImpl {

    public DawnsireSunstarDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 10+
        // Whenever you attack, Dawnsire deals 100 damage to up to one target creature or planeswalker.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new DamageTargetEffect(100), 1);
        ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1));
        this.addAbility(new StationLevelAbility(10).withLevelAbility(ability));

        // STATION 20+
        // Flying
        // 20/20
        this.addAbility(new StationLevelAbility(20)
                .withLevelAbility(FlyingAbility.getInstance())
                .withPT(20, 20));
    }

    private DawnsireSunstarDreadnought(final DawnsireSunstarDreadnought card) {
        super(card);
    }

    @Override
    public DawnsireSunstarDreadnought copy() {
        return new DawnsireSunstarDreadnought(this);
    }
}
