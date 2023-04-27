
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
public final class WormwoodDryad extends CardImpl {

    public WormwoodDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {G}: Wormwood Dryad gains forestwalk until end of turn and deals 1 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new ForestwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addEffect(new DamageControllerEffect(1).setText("and deals 1 damage to you"));
        this.addAbility(ability);

        // {B}: Wormwood Dryad gains swampwalk until end of turn and deals 1 damage to you.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new SwampwalkAbility(false), Duration.EndOfTurn), new ManaCostsImpl<>("{B}"));
        ability.addEffect(new DamageControllerEffect(1).setText("and deals 1 damage to you"));
        this.addAbility(ability);
    }

    private WormwoodDryad(final WormwoodDryad card) {
        super(card);
    }

    @Override
    public WormwoodDryad copy() {
        return new WormwoodDryad(this);
    }
}
