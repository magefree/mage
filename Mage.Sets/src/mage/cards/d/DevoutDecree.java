package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevoutDecree extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker that's black or red");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.RED)
        ));
    }

    public DevoutDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature or planeswalker that's black or red. Scry 1.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DevoutDecree(final DevoutDecree card) {
        super(card);
    }

    @Override
    public DevoutDecree copy() {
        return new DevoutDecree(this);
    }
}
