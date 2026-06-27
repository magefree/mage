package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class HYDRAInfiltration extends CardImpl {

    public HYDRAInfiltration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // When this enchantment enters, target opponent discards two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever a creature you control attacks alone, target opponent loses 1 life and you gain 1 life.
        Ability ability2 = new AttacksAloneControlledTriggeredAbility(
            new LoseLifeTargetEffect(1)
        );
        ability2.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability2.addTarget(new TargetOpponent());
        this.addAbility(ability2);
    }

    private HYDRAInfiltration(final HYDRAInfiltration card) {
        super(card);
    }

    @Override
    public HYDRAInfiltration copy() {
        return new HYDRAInfiltration(this);
    }
}
