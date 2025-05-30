package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ScorpionDragonToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MagdaTheHoardmaster extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    public MagdaTheHoardmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you commit a crime, create a tapped Treasure token. This ability triggers only once each turn.
        this.addAbility(
                new CommittedCrimeTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 1, true))
                        .setTriggersLimitEachTurn(1)
        );

        // Sacrifice three Treasures: Create a 4/4 red Scorpion Dragon creature token with flying and haste. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new ScorpionDragonToken()),
                new SacrificeTargetCost(3, filter)
        ));
    }

    private MagdaTheHoardmaster(final MagdaTheHoardmaster card) {
        super(card);
    }

    @Override
    public MagdaTheHoardmaster copy() {
        return new MagdaTheHoardmaster(this);
    }
}
