package mage.cards.a;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetSpellOrPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AetherGust extends CardImpl {

    private static final FilterSpellOrPermanent filter
            = new FilterSpellOrPermanent("spell or permanent that's red or green");
    private static final Predicate<MageObject> predicate = Predicates.or(
            new ColorPredicate(ObjectColor.RED),
            new ColorPredicate(ObjectColor.GREEN)
    );

    static {
        filter.getPermanentFilter().add(predicate);
        filter.getSpellFilter().add(predicate);
    }

    public AetherGust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose target spell or permanent that's red or green. Its owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(
                "choose target spell or permanent that's red or green. " +
                        "Its owner puts it on the top or bottom of their library"
        ));
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(filter));
    }

    private AetherGust(final AetherGust card) {
        super(card);
    }

    @Override
    public AetherGust copy() {
        return new AetherGust(this);
    }
}
