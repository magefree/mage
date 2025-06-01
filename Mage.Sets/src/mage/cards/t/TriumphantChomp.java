package mage.cards.t;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MaximumDynamicValue;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphantChomp extends CardImpl {
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR);
    private static final DynamicValue xValue = new MaximumDynamicValue(
            StaticValue.get(2),
            new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filter)
    );

    public TriumphantChomp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Triumphant Chomp deals damage to target creature equal to 2 or the greatest power among Dinosaurs you control, whichever is greater.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target creature equal to 2 or the greatest power among Dinosaurs you control, whichever is greater"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(new ValueHint("Current damage", xValue));
    }

    private TriumphantChomp(final TriumphantChomp card) {
        super(card);
    }

    @Override
    public TriumphantChomp copy() {
        return new TriumphantChomp(this);
    }
}
