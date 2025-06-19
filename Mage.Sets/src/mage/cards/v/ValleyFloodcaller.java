package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author jimga150
 */
public final class ValleyFloodcaller extends CardImpl {

    private static final FilterCard nonCreatureFilter = new FilterCard("noncreature spells");

    private static final FilterCreaturePermanent creatureFilter =
            new FilterCreaturePermanent("Birds, Frogs, Otters, and Rats");

    static {
        nonCreatureFilter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        creatureFilter.add(TargetController.YOU.getControllerPredicate());
        creatureFilter.add(Predicates.or(
                SubType.BIRD.getPredicate(),
                SubType.FROG.getPredicate(),
                SubType.OTTER.getPredicate(),
                SubType.RAT.getPredicate()
        ));
    }

    public ValleyFloodcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may cast noncreature spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, nonCreatureFilter)
        ));

        // Whenever you cast a noncreature spell, Birds, Frogs, Otters, and Rats you control get +1/+1 until end of turn.
        // Untap them.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn, creatureFilter),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new UntapAllControllerEffect(creatureFilter).setText("Untap them"));
        this.addAbility(ability);
    }

    private ValleyFloodcaller(final ValleyFloodcaller card) {
        super(card);
    }

    @Override
    public ValleyFloodcaller copy() {
        return new ValleyFloodcaller(this);
    }
}
