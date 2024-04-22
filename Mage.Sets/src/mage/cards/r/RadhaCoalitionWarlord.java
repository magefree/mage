package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadhaCoalitionWarlord extends CardImpl {

    public RadhaCoalitionWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Domain -- Whenever Radha, Coalition Warlord becomes tapped, another target creature you control gets +X/+X until end of turn, where X is the number of basic land types among lands you control.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new BoostTargetEffect(
                DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability.setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private RadhaCoalitionWarlord(final RadhaCoalitionWarlord card) {
        super(card);
    }

    @Override
    public RadhaCoalitionWarlord copy() {
        return new RadhaCoalitionWarlord(this);
    }
}
