
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class IsaoEnlightenedBushi extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Samurai");

    static {
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public IsaoEnlightenedBushi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Isao, Enlightened Bushi can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        this.addAbility(new BushidoAbility(2));
        // {2}: Regenerate target Samurai.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private IsaoEnlightenedBushi(final IsaoEnlightenedBushi card) {
        super(card);
    }

    @Override
    public IsaoEnlightenedBushi copy() {
        return new IsaoEnlightenedBushi(this);
    }
}
