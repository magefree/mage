

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class ShinkaTheBloodsoakedKeep extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ShinkaTheBloodsoakedKeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.supertype.add(SuperType.LEGENDARY);
        this.addAbility(new RedManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ShinkaTheBloodsoakedKeep(final ShinkaTheBloodsoakedKeep card) {
        super(card);
    }

    @Override
    public ShinkaTheBloodsoakedKeep copy() {
        return new ShinkaTheBloodsoakedKeep(this);
    }

}
