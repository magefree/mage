
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Dreamcatcher extends CardImpl {

    public Dreamcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a Spirit or Arcane spell, you may sacrifice Dreamcatcher. If you do, draw a card.
        Ability ability = new SpellCastControllerTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true,
                "Whenever you cast a Spirit or Arcane spell, you may sacrifice {this}. If you do, draw a card.");
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private Dreamcatcher(final Dreamcatcher card) {
        super(card);
    }

    @Override
    public Dreamcatcher copy() {
        return new Dreamcatcher(this);
    }
}
