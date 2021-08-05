package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonsRageChanneler extends CardImpl {

    public DragonsRageChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a noncreature spell, surveil 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Delirium — As long as there are four or more card types among cards in your graveyard, Dragon's Rage Channeler gets +2/+2, has flying, and attacks each combat if able.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
                DeliriumCondition.instance, "as long as there are four or more " +
                "card types among cards in your graveyard, {this} gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield
        ), DeliriumCondition.instance, ", has flying"));
        ability.addEffect(new ConditionalRequirementEffect(
                new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield),
                DeliriumCondition.instance, ", and attacks each combat if able"
        ));
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        this.addAbility(ability.addHint(CardTypesInGraveyardHint.YOU));
    }

    private DragonsRageChanneler(final DragonsRageChanneler card) {
        super(card);
    }

    @Override
    public DragonsRageChanneler copy() {
        return new DragonsRageChanneler(this);
    }
}
