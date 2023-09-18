
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class WormwoodTreefolk extends CardImpl {

    public WormwoodTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {G}{G}: Wormwood Treefolk gains forestwalk until end of turn and deals 2 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new ForestwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl<>("{G}{G}"));
        ability.addEffect(new DamageControllerEffect(2).setText("and deals 2 damage to you"));
        this.addAbility(ability);

        // {B}{B}: Wormwood Treefolk gains swampwalk until end of turn and deals 2 damage to you.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new SwampwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl<>("{B}{B}"));
        ability.addEffect(new DamageControllerEffect(2).setText("and deals 2 damage to you"));
        this.addAbility(ability);
    }

    private WormwoodTreefolk(final WormwoodTreefolk card) {
        super(card);
    }

    @Override
    public WormwoodTreefolk copy() {
        return new WormwoodTreefolk(this);
    }
}
