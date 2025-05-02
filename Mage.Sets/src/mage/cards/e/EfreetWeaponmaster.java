package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrTurnedFaceUpTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EfreetWeaponmaster extends CardImpl {

    public EfreetWeaponmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}{W}");
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Efreet Weaponmaster enters the battlefield or is turned face up, another target creature you control gets +3/+0 until end of turn.
        Ability ability = new EntersBattlefieldOrTurnedFaceUpTriggeredAbility(
                new BoostTargetEffect(3, 0, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Morph {2}{U}{R}{W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{2}{U}{R}{W}")));
    }

    private EfreetWeaponmaster(final EfreetWeaponmaster card) {
        super(card);
    }

    @Override
    public EfreetWeaponmaster copy() {
        return new EfreetWeaponmaster(this);
    }
}
