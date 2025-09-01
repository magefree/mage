package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShrikeHarpy extends CardImpl {

    public ShrikeHarpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.HARPY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tribute 2</i>
        this.addAbility(new TributeAbility(2));

        // When Shrike Harpy enters the battlefield, if tribute wasn't paid, target opponent sacrifices a creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, "target opponent"
        )).withInterveningIf(TributeNotPaidCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ShrikeHarpy(final ShrikeHarpy card) {
        super(card);
    }

    @Override
    public ShrikeHarpy copy() {
        return new ShrikeHarpy(this);
    }
}
