package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MisterFantastic extends CardImpl {

    public MisterFantastic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, draw a card.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance)
                .addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // {R}{G}{W}{U}, {T}: Copy target triggered ability you control twice. You may choose new targets for the copies.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect()
                .setText("copy target triggered ability you control twice"), new ManaCostsImpl<>("{R}{G}{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CopyTargetStackObjectEffect().setText("You may choose new targets for the copies."));
        ability.addTarget(new TargetStackObject(StaticFilters.FILTER_CONTROLLED_TRIGGERED_ABILITY));
        this.addAbility(ability);
    }

    private MisterFantastic(final MisterFantastic card) {
        super(card);
    }

    @Override
    public MisterFantastic copy() {
        return new MisterFantastic(this);
    }
}
