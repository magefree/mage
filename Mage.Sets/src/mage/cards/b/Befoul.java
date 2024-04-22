package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX
 */
public final class Befoul extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("land or nonblack creature");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                Predicates.and(
                    Predicates.not(new ColorPredicate(ObjectColor.BLACK)),
                    CardType.CREATURE.getPredicate())));
    }
    public Befoul (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // Destroy target land or nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Befoul(final Befoul card) {
        super(card);
    }

    @Override
    public Befoul copy() {
        return new Befoul(this);
    }

}
