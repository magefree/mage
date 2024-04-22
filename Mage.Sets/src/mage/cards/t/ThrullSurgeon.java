package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ThrullSurgeon extends CardImpl {

    public ThrullSurgeon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}, Sacrifice Thrull Surgeon: Look at target player's hand and choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new LookTargetHandChooseDiscardEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ThrullSurgeon(final ThrullSurgeon card) {
        super(card);
    }

    @Override
    public ThrullSurgeon copy() {
        return new ThrullSurgeon(this);
    }
}
