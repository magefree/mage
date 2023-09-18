package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SmeltWardMinotaur extends CardImpl {

    public SmeltWardMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell, target creature an opponent controls can't block this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private SmeltWardMinotaur(final SmeltWardMinotaur card) {
        super(card);
    }

    @Override
    public SmeltWardMinotaur copy() {
        return new SmeltWardMinotaur(this);
    }
}
