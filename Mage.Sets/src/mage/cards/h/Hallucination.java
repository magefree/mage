package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.util.functions.AbilityApplier;

/**
 *
 * @author NinthWorld
 */
public final class Hallucination extends CardImpl {

    public Hallucination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // You may have Hallucination enter the battlefield as a copy of any creature on the battlefield, except it's still 0/4.
        Ability ability = new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE,
                        new AbilityApplier(new SimpleStaticAbility(Zone.BATTLEFIELD,
                                new SetPowerToughnessSourceEffect(0, 4, Duration.WhileOnBattlefield))))
                        .setText("You may have Hallucination enter the battlefield as a copy of any creature on the battlefield, except it's still 0/4"), true);
        this.addAbility(ability);
    }

    public Hallucination(final Hallucination card) {
        super(card);
    }

    @Override
    public Hallucination copy() {
        return new Hallucination(this);
    }
}
