package mage.cards.a;

import mage.Mana;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArlinnTheMoonsFury extends CardImpl {

    public ArlinnTheMoonsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARLINN);
        this.setStartingLoyalty(4);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Nightbound
        this.addAbility(new NightboundAbility());

        // +2: Add {R}{G}.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(new Mana(
                0, 0, 0, 1, 1, 0, 0, 0
        )), 2));

        // 0: Until end of turn, Arlinn, the Moon's Fury becomes a 5/5 Werewolf creature with trample, indestructible, and haste.
        this.addAbility(new LoyaltyAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(5, 5, "5/5 Werewolf creature with trample, indestructible, and haste")
                        .withSubType(SubType.WEREWOLF)
                        .withAbility(TrampleAbility.getInstance())
                        .withAbility(IndestructibleAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                null,
                Duration.EndOfTurn)
                .withDurationRuleAtStart(true), 0));
    }

    private ArlinnTheMoonsFury(final ArlinnTheMoonsFury card) {
        super(card);
    }

    @Override
    public ArlinnTheMoonsFury copy() {
        return new ArlinnTheMoonsFury(this);
    }
}
