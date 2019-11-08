package mage.cards.r;

import mage.MageInt;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RealmCloakedGiant extends AdventureCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("non-Giant creatures");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.GIANT)));
    }

    public RealmCloakedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{5}{W}{W}", "{5}{W}{W}", "{3}{W}{W}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Cast Off
        // Destroy all non-Giant creatures.
        this.getAdventureSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private RealmCloakedGiant(final RealmCloakedGiant card) {
        super(card);
    }

    @Override
    public RealmCloakedGiant copy() {
        return new RealmCloakedGiant(this);
    }
}
// clock up
