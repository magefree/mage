
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShadowbornApostle extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Demon creature card");
    private static final FilterControlledCreaturePermanent filterApostle = new FilterControlledCreaturePermanent("creatures named Shadowborn Apostle");
    static {
        filter.add(SubType.DEMON.getPredicate());
        filterApostle.add(new NamePredicate("Shadowborn Apostle"));
    }

    public ShadowbornApostle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // A deck can have any number of cards named Shadowborn Apostle.
        this.getSpellAbility().addEffect(new InfoEffect("A deck can have any number of cards named Shadowborn Apostle."));
        // {B}, Sacrifice six creatures named Shadowborn Apostle: Search your library for a Demon creature and put it onto the battlefield. Then shuffle your library.
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(6,6,filterApostle, false)));
        this.addAbility(ability);
    }

    private ShadowbornApostle(final ShadowbornApostle card) {
        super(card);
    }

    @Override
    public ShadowbornApostle copy() {
        return new ShadowbornApostle(this);
    }
}
