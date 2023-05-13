package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AshnodZombieToken;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class AshnodFleshMechanist extends CardImpl {

    public AshnodFleshMechanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Ashnod, Flesh Mechanist attacks, you may sacrifice another creature. If you do, create a tapped Powerstone token.
        this.addAbility(new AttacksTriggeredAbility(
                new DoIfCostPaid(
                        new CreateTokenEffect(
                                new PowerstoneToken(), 1, true, false
                        ), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))
                ), false
        ));

        // {5}, Exile a creature card from your graveyard: Create a tapped 3/3 colorless Zombie artifact creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new AshnodZombieToken(), 1, true, false), new GenericManaCost(5)
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)));
        this.addAbility(ability);
    }

    private AshnodFleshMechanist(final AshnodFleshMechanist card) {
        super(card);
    }

    @Override
    public AshnodFleshMechanist copy() {
        return new AshnodFleshMechanist(this);
    }
}
