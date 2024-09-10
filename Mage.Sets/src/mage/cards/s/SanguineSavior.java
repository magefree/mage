package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanguineSavior extends CardImpl {

    public SanguineSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Disguise {W/B}{W/B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{W/B}{W/B}")));

        // When Sanguine Savior is turned face up, another target creature you control gains lifelink until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainAbilityTargetEffect(LifelinkAbility.getInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private SanguineSavior(final SanguineSavior card) {
        super(card);
    }

    @Override
    public SanguineSavior copy() {
        return new SanguineSavior(this);
    }
}
