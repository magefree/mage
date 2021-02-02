package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PrescientChimera extends CardImpl {

    public PrescientChimera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.CHIMERA);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast an instant or sorcery spell, scry 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ScryEffect(1), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false));
    }

    private PrescientChimera(final PrescientChimera card) {
        super(card);
    }

    @Override
    public PrescientChimera copy() {
        return new PrescientChimera(this);
    }
}
