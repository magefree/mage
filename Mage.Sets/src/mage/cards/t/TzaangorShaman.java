package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TzaangorShaman extends CardImpl {

    public TzaangorShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.subtype.add(SubType.MUTANT, SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sorcerous Elixir — Whenever Tzaangor Shaman deals combat damage to a player, copy the next instant or
        // sorcery spell you cast this turn when you cast it. You may choose new targets for the copy.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new CopyNextSpellDelayedTriggeredAbility())
                .setText("copy the next instant or sorcery spell you cast this turn when you cast it. You may choose new targets for the copy."), false)
                .withFlavorWord("Sorcerous Elixir")
        );
    }

    private TzaangorShaman(final TzaangorShaman card) {
        super(card);
    }

    @Override
    public TzaangorShaman copy() {
        return new TzaangorShaman(this);
    }
}
