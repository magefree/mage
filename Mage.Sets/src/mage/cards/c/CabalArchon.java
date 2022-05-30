
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class CabalArchon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Cleric");

    static {
        filter.add(SubType.CLERIC.getPredicate());
    }

    public CabalArchon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}, Sacrifice a Cleric: Target player loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2), new ManaCostsImpl<>("{B}"));
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CabalArchon(final CabalArchon card) {
        super(card);
    }

    @Override
    public CabalArchon copy() {
        return new CabalArchon(this);
    }
}
