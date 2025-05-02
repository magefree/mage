package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GleefulArsonist extends CardImpl {

    public GleefulArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever an opponent casts a noncreature spell, Gleeful Arsonist deals damage equal to its power to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE).setText("{this} deals damage equal to its power to that player"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.PLAYER
        ));

        // Undying
        this.addAbility(new UndyingAbility());
    }

    private GleefulArsonist(final GleefulArsonist card) {
        super(card);
    }

    @Override
    public GleefulArsonist copy() {
        return new GleefulArsonist(this);
    }
}
