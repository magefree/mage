package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.IsMainPhaseCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinsAcuity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public DovinsAcuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // When Dovin's Acuity enters the battlefield, you gain 2 life and draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and draw a card"));
        this.addAbility(ability);

        // Whenever you cast an instant spell during your main phase, you may return Dovin's Acuity to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ReturnToHandSourceEffect(true), filter, true
        ).withTriggerCondition(IsMainPhaseCondition.YOUR).setTriggerPhrase("Whenever you cast an instant spell during your main phase, "));
    }

    private DovinsAcuity(final DovinsAcuity card) {
        super(card);
    }

    @Override
    public DovinsAcuity copy() {
        return new DovinsAcuity(this);
    }
}
