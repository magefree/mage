package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TurnPhase;
import mage.filter.FilterSpell;
import mage.game.Game;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinsAcuity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell();

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
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new ReturnToHandSourceEffect(true), filter, true
                ), DovinsAcuityCondition.instance,
                "Whenever you cast an instant spell during your main phase, " +
                        "you may return {this} to its owner's hand."
        ));
    }

    private DovinsAcuity(final DovinsAcuity card) {
        super(card);
    }

    @Override
    public DovinsAcuity copy() {
        return new DovinsAcuity(this);
    }
}

enum DovinsAcuityCondition implements Condition {

    instance;
    private static final Set<TurnPhase> turnPhases = EnumSet.of(TurnPhase.PRECOMBAT_MAIN, TurnPhase.POSTCOMBAT_MAIN);

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId())
                && turnPhases.contains(game.getTurn().getPhase().getType());
    }
}