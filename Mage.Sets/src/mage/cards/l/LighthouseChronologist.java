package mage.cards.l;

import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.hint.common.NotMyTurnHint;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, noxx
 */
public final class LighthouseChronologist extends LevelerCard {

    public LighthouseChronologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{U}")));

        // LEVEL 4-6
        // 2/4
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();

        // LEVEL 7+
        // 3/5
        // At the beginning of each end step, if it's not your turn, take an extra turn after this one.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(false), TargetController.ANY, NotMyTurnCondition.instance, false)
                .addHint(NotMyTurnHint.instance));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(4, 6, abilities1, 2, 4),
                new LevelerCardBuilder.LevelAbility(7, -1, abilities2, 3, 5)
        ));
        setMaxLevelCounters(7);
    }

    private LighthouseChronologist(final LighthouseChronologist card) {
        super(card);
    }

    @Override
    public LighthouseChronologist copy() {
        return new LighthouseChronologist(this);
    }

}
