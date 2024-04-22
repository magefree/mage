package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class VedalkenArchmage extends CardImpl {
    public VedalkenArchmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever you cast an artifact spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private VedalkenArchmage(final VedalkenArchmage card) {
        super(card);
    }

    @Override
    public VedalkenArchmage copy() {
        return new VedalkenArchmage(this);
    }
}
