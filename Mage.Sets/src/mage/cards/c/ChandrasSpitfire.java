package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.OpponentDealtNoncombatDamageTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ChandrasSpitfire extends CardImpl {

    public ChandrasSpitfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent is dealt noncombat damage, Chandra’s Spitfire gets +3/+0 until end of turn.
        this.addAbility(new OpponentDealtNoncombatDamageTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn)));
    }

    private ChandrasSpitfire(final ChandrasSpitfire card) {
        super(card);
    }

    @Override
    public ChandrasSpitfire copy() {
        return new ChandrasSpitfire(this);
    }

}
