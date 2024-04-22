package mage.cards.g;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageWithExcessEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GandalfsSanction extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(
            new FilterInstantOrSorceryCard("instant and sorcery cards")
    );
    private static final Hint hint = new ValueHint("Instants and sorceries in your graveyard", xValue);

    public GandalfsSanction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Gandalf's Sanction deals X damage to target creature, where X is the number of instant and sorcery cards in your graveyard. Excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new DamageWithExcessEffect(xValue));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private GandalfsSanction(final GandalfsSanction card) {
        super(card);
    }

    @Override
    public GandalfsSanction copy() {
        return new GandalfsSanction(this);
    }
}
