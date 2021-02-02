package mage.cards.b;

import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class BlindingRadiance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creatures your opponents control with toughness 2 or less");
    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public BlindingRadiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Tap all creatures your opponents control with toughness 2 or less.
        TapAllEffect effect = new TapAllEffect(filter);
        this.getSpellAbility().addEffect(effect);
    }

    private BlindingRadiance(final BlindingRadiance card) {
        super(card);
    }

    @Override
    public BlindingRadiance copy() {
        return new BlindingRadiance(this);
    }
}