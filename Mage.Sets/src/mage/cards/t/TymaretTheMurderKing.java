
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class TymaretTheMurderKing extends CardImpl {

    public TymaretTheMurderKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}, Sacrifice another creature: Tymaret, the Murder King deals 2 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false)));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
        // {1}{B}, Sacrifice a creature: Return Tymaret from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);

    }

    private TymaretTheMurderKing(final TymaretTheMurderKing card) {
        super(card);
    }

    @Override
    public TymaretTheMurderKing copy() {
        return new TymaretTheMurderKing(this);
    }
}
