package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class SurtlandElementalist extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Giant card from your hand");
    private static final FilterCard filter2 = new FilterInstantOrSorceryCard("an instant or sorcery spell");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public SurtlandElementalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // As an additional cost to cast this spell, reveal a Giant card from your hand or pay {2}.
        this.getSpellAbility().addCost(new OrCost(
                new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(2),
                "reveal a Giant card from your hand or pay {2}"));

        // Whenever Surtland Elementalist attacks, you may cast an instant or sorcery spell from your hand without paying its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new CastFromHandForFreeEffect(filter2), true));
    }

    private SurtlandElementalist(final SurtlandElementalist card) {
        super(card);
    }

    @Override
    public SurtlandElementalist copy() {
        return new SurtlandElementalist(this);
    }
}
