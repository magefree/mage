package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class GreelMindRaker extends CardImpl {

    private static final FilterCard filter = new FilterCard("two cards");

    public GreelMindRaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{B}, {tap}, Discard two cards: Target player discards X cards at random.
        Ability ability = new SimpleActivatedAbility(new DiscardTargetEffect(
                ManacostVariableValue.REGULAR, true
        ), new ManaCostsImpl<>("{X}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, filter)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GreelMindRaker(final GreelMindRaker card) {
        super(card);
    }

    @Override
    public GreelMindRaker copy() {
        return new GreelMindRaker(this);
    }
}
