package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Moderation extends CardImpl {

    public Moderation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.YOU)));

        // Whenever you cast a spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private Moderation(final Moderation card) {
        super(card);
    }

    @Override
    public Moderation copy() {
        return new Moderation(this);
    }
}
