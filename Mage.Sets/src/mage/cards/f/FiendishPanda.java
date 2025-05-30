package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class FiendishPanda extends CardImpl {

    public FiendishPanda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put a +1/+1 counter on this creature.
        this.addAbility(new GainLifeControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));

        // When this creature dies, return another target non-Bear creature card with mana value less than or equal to this creature's power from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return another target non-Bear creature card "
                        + "with mana value less than or equal to this creature's power "
                        + "from your graveyard to the battlefield")
        );
        ability.setTargetAdjuster(FiendishPandaAdjuster.instance);
        this.addAbility(ability);
    }

    private FiendishPanda(final FiendishPanda card) {
        super(card);
    }

    @Override
    public FiendishPanda copy() {
        return new FiendishPanda(this);
    }
}

enum FiendishPandaAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int power = 0;
        Effects effects = ability.getEffects();
        if (!effects.isEmpty()) {
            Object died = effects.get(0).getValue("permanentLeftBattlefield");
            if (died instanceof Permanent) {
                power = ((Permanent) died).getPower().getValue();
            }
        }
        FilterCreatureCard filter = new FilterCreatureCard(
                "another target non-Bear creature card "
                + "with mana value less than or equal to this creature's power from your graveyard"
        );
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SubType.BEAR.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, power));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}
