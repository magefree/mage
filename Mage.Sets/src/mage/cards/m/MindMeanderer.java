package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MindMeanderer extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
        new FilterControlledPermanent(SubType.JACE) // Jace subtype is only valid on a Planeswalker
    );

    public MindMeanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This creature has vigilance as long as you control a Jace planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
            condition, "{this} has vigilance as long as you control a Jace planeswalker"
        )));

        // When this creature enters, it fights up to one target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
            .setText("it fights up to one target creature an opponent controls"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
        this.addAbility(ability);
    }

    private MindMeanderer(final MindMeanderer card) {
        super(card);
    }

    @Override
    public MindMeanderer copy() {
        return new MindMeanderer(this);
    }
}
