
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AbyssalNocturnus extends CardImpl {

    public AbyssalNocturnus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever an opponent discards a card, Abyssal Nocturnus gets +2/+2 and gains fear until end of turn.
        Effect boostEffect = new BoostSourceEffect(2, 2, Duration.EndOfTurn);
        boostEffect.setText("{this} gets +2/+2");
        Ability ability = new DiscardsACardOpponentTriggeredAbility(boostEffect, false); // not optional
        Effect fearEffect = new GainAbilitySourceEffect(FearAbility.getInstance(), Duration.EndOfTurn);
        fearEffect.setText("and gains fear until end of turn");
        ability.addEffect(fearEffect);
        this.addAbility(ability);
    }

    private AbyssalNocturnus(final AbyssalNocturnus card) {
        super(card);
    }

    @Override
    public AbyssalNocturnus copy() {
        return new AbyssalNocturnus(this);
    }
}
