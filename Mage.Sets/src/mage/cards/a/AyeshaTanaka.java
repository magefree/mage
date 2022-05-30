
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author L_J
 */
public final class AyeshaTanaka extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
    }

    public AyeshaTanaka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // {T}: Counter target activated ability from an artifact source unless that ability's controller pays {W}.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new ManaCostsImpl<>("{W}")), new TapSourceCost());
        ability.addTarget(new TargetActivatedAbility(filter));
        this.addAbility(ability);
    }

    private AyeshaTanaka(final AyeshaTanaka card) {
        super(card);
    }

    @Override
    public AyeshaTanaka copy() {
        return new AyeshaTanaka(this);
    }
}
