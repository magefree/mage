package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarvesterOfMisery extends CardImpl {

    public HarvesterOfMisery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Harvester of Misery enters the battlefield, other creatures get -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(
                -2, -2, Duration.EndOfTurn, true
        )));

        // {1}{B}, Discard Harvester of Misery: Target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new BoostTargetEffect(-2, -2), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HarvesterOfMisery(final HarvesterOfMisery card) {
        super(card);
    }

    @Override
    public HarvesterOfMisery copy() {
        return new HarvesterOfMisery(this);
    }
}
