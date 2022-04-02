package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class NeedleSpecter extends CardImpl {

    public NeedleSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Whenever Needle Specter deals combat damage to a player, that player discards that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DiscardTargetEffect(SavedDamageValue.MANY),
                false, true));
    }

    private NeedleSpecter(final NeedleSpecter card) {
        super(card);
    }

    @Override
    public NeedleSpecter copy() {
        return new NeedleSpecter(this);
    }
}
