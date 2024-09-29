package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlistercoilWeird extends CardImpl {

    public BlistercoilWeird(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}");
        this.subtype.add(SubType.WEIRD);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast an instant or sorcery spell, Blistercoil Weird gets +1/+1 until end of turn. Untap it.
        Ability ability = new SpellCastControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        ability.addEffect(new UntapSourceEffect().setText("Untap it"));
        this.addAbility(ability);

    }

    private BlistercoilWeird(final BlistercoilWeird card) {
        super(card);
    }

    @Override
    public BlistercoilWeird copy() {
        return new BlistercoilWeird(this);
    }
}
