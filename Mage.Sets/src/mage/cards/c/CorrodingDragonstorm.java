package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorrodingDragonstorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public CorrodingDragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When this enchantment enters, each opponent loses 2 life and you gain 2 life. Surveil 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addEffect(new SurveilEffect(2));
        this.addAbility(ability);

        // When a Dragon you control enters, return this enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter));
    }

    private CorrodingDragonstorm(final CorrodingDragonstorm card) {
        super(card);
    }

    @Override
    public CorrodingDragonstorm copy() {
        return new CorrodingDragonstorm(this);
    }
}
