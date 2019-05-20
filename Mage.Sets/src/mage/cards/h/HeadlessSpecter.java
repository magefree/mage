package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeadlessSpecter extends CardImpl {

    public HeadlessSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hellbent — Whenever Headless Specter deals combat damage to a player, if you have no cards in hand, that player discards a card at random.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DiscardTargetEffect(1, true), false, true
                ), HellbentCondition.instance, "<i>Hellbent</i> &mdash; Whenever {this} deals combat damage " +
                "to a player, if you have no cards in hand, that player discards a card at random."
        ));
    }

    private HeadlessSpecter(final HeadlessSpecter card) {
        super(card);
    }

    @Override
    public HeadlessSpecter copy() {
        return new HeadlessSpecter(this);
    }
}
