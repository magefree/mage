package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Dreamcatcher extends CardImpl {

    public Dreamcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a Spirit or Arcane spell, you may sacrifice Dreamcatcher. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new SacrificeSourceCost()
        ), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, false));
    }

    private Dreamcatcher(final Dreamcatcher card) {
        super(card);
    }

    @Override
    public Dreamcatcher copy() {
        return new Dreamcatcher(this);
    }
}
