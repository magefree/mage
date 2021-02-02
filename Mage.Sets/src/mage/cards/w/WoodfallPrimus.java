
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class WoodfallPrimus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");
    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public WoodfallPrimus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Woodfall Primus enters the battlefield, destroy target noncreature permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // Persist
        this.addAbility(new PersistAbility());
    }

    private WoodfallPrimus(final WoodfallPrimus card) {
        super(card);
    }

    @Override
    public WoodfallPrimus copy() {
        return new WoodfallPrimus(this);
    }
}
