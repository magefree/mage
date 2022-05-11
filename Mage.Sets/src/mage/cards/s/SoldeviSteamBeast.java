package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class SoldeviSteamBeast extends CardImpl {

    public SoldeviSteamBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Soldevi Steam Beast becomes tapped, target opponent gains 2 life.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new GainLifeTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {2}: Regenerate Soldevi Steam Beast.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new GenericManaCost(2)));
    }

    private SoldeviSteamBeast(final SoldeviSteamBeast card) {
        super(card);
    }

    @Override
    public SoldeviSteamBeast copy() {
        return new SoldeviSteamBeast(this);
    }
}
