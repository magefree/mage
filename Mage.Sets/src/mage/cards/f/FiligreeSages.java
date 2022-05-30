

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class FiligreeSages extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public FiligreeSages (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public FiligreeSages (final FiligreeSages card) {
        super(card);
    }

    @Override
    public FiligreeSages copy() {
        return new FiligreeSages(this);
    }

}
