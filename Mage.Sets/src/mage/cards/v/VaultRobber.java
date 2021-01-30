package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VaultRobber extends CardImpl {

    public VaultRobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}, {T}, Exile a creature card from your graveyard: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new TreasureToken()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_A
        )));
        this.addAbility(ability);
    }

    private VaultRobber(final VaultRobber card) {
        super(card);
    }

    @Override
    public VaultRobber copy() {
        return new VaultRobber(this);
    }
}
