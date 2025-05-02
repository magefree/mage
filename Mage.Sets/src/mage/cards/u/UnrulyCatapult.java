package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class UnrulyCatapult extends CardImpl {

    public UnrulyCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Unruly Catapult deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost()
        ));

        // Whenever you cast an instant or sorcery spell, untap Unruly Catapult-Alchemist.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new UntapSourceEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private UnrulyCatapult(final UnrulyCatapult card) {
        super(card);
    }

    @Override
    public UnrulyCatapult copy() {
        return new UnrulyCatapult(this);
    }
}