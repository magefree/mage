
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class Fluxcharger extends CardImpl {

    public Fluxcharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{R}");
        this.subtype.add(SubType.WEIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast an instant or sorcery spell, you may switch Fluxcharger's power and toughness until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SwitchPowerToughnessSourceEffect(Duration.EndOfTurn), StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, true));

    }

    public Fluxcharger(final Fluxcharger card) {
        super(card);
    }

    @Override
    public Fluxcharger copy() {
        return new Fluxcharger(this);
    }
}
