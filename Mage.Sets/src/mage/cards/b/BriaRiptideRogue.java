package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BriaRiptideRogue extends CardImpl {

    public BriaRiptideRogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Other creatures you control have prowess.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ProwessAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Whenever you cast a noncreature spell, target creature you control can't be blocked this turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CantBeBlockedTargetEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, true
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BriaRiptideRogue(final BriaRiptideRogue card) {
        super(card);
    }

    @Override
    public BriaRiptideRogue copy() {
        return new BriaRiptideRogue(this);
    }
}
