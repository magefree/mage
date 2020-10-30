package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LullmagesFamiliar extends CardImpl {

    public LullmagesFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Whenever you cast a kicked spell, you gain 2 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GainLifeEffect(2), StaticFilters.FILTER_SPELL_KICKED_A, false
        ));
    }

    private LullmagesFamiliar(final LullmagesFamiliar card) {
        super(card);
    }

    @Override
    public LullmagesFamiliar copy() {
        return new LullmagesFamiliar(this);
    }
}
