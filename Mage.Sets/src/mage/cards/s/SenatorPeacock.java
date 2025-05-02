package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SenatorPeacock extends CardImpl {

    public SenatorPeacock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Artifacts you control are Clues in addition to their other types and have "{2}, Sacrifice this artifact: Draw a card."
        Ability ability = new SimpleStaticAbility(new AddCardSubtypeAllEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS, SubType.CLUE, null
        ).setText("artifacts you control are Clues in addition to their other types"));
        ability.addEffect(new GainAbilityAllEffect(
                new ClueAbility(false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                "and have \"{2}, Sacrifice this artifact: Draw a card.\""
        ));
        this.addAbility(ability);

        // Whenever you sacrifice a Clue, target creature can't be blocked this turn.
        ability = new SacrificePermanentTriggeredAbility(new CantBeBlockedTargetEffect(), StaticFilters.FILTER_CONTROLLED_CLUE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SenatorPeacock(final SenatorPeacock card) {
        super(card);
    }

    @Override
    public SenatorPeacock copy() {
        return new SenatorPeacock(this);
    }
}
