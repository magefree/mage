package mage.cards.b;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzOfTheThunderRaptor extends CardImpl {

    public static final FilterInstantOrSorceryCard filter
            = new FilterInstantOrSorceryCard("instant and sorcery cards");
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public BlitzOfTheThunderRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Blitz of the Thunder-Raptor deals damage to target creature or planeswalker equal to the number of instant and sorcery cards in your graveyard. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target creature or planeswalker " +
                        "equal to the number of instant and sorcery cards in your graveyard"));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect()
                .setText("If that creature or planeswalker would die this turn, exile it instead"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BlitzOfTheThunderRaptor(final BlitzOfTheThunderRaptor card) {
        super(card);
    }

    @Override
    public BlitzOfTheThunderRaptor copy() {
        return new BlitzOfTheThunderRaptor(this);
    }
}
