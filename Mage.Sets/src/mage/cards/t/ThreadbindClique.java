package mage.cards.t;

import mage.MageInt;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThreadbindClique extends AdventureCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public ThreadbindClique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{U}", "Rip the Seams", "{2}{W}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Rip the Seams
        // Destroy target tapped creature.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        this.finalizeAdventure();
    }

    private ThreadbindClique(final ThreadbindClique card) {
        super(card);
    }

    @Override
    public ThreadbindClique copy() {
        return new ThreadbindClique(this);
    }
}
