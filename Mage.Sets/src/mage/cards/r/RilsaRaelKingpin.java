package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RilsaRaelKingpin extends CardImpl {

    public RilsaRaelKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Rilsa Rael, Kingpin enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Whenever you attack, target attacking creature gains deathtouch until end of turn. If you've completed a dungeon, that creature also gets +5/+0 and gains first strike and menace until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new GainAbilityTargetEffect(DeathtouchAbility.getInstance()), 1
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(5, 0), CompletedDungeonCondition.instance,
                "If you've completed a dungeon, that creature also gets +5/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()),
                CompletedDungeonCondition.instance, "and gains first strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(new MenaceAbility(false)),
                CompletedDungeonCondition.instance, "and menace until end of turn"
        ));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability.addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private RilsaRaelKingpin(final RilsaRaelKingpin card) {
        super(card);
    }

    @Override
    public RilsaRaelKingpin copy() {
        return new RilsaRaelKingpin(this);
    }
}
