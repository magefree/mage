package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WindriderWizard extends CardImpl {

    public WindriderWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant, sorcery, or Wizard spell, you may draw a card. If you do, discard a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true),
                StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD,
                false,
                "Whenever you cast an instant, sorcery, or Wizard spell, you may draw a card. " +
                        "If you do, discard a card.")
        );
    }

    private WindriderWizard(final WindriderWizard card) {
        super(card);
    }

    @Override
    public WindriderWizard copy() {
        return new WindriderWizard(this);
    }
}
