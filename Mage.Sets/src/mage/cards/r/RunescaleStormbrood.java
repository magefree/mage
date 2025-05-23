package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.OmenCard;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author Jmlundeen
 */
public final class RunescaleStormbrood extends OmenCard {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 2 or less");
    private static final FilterSpell castFilter = new FilterSpell("a noncreature spell or a Dragon spell");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
        castFilter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.DRAGON.getPredicate()
        ));
    }

    public RunescaleStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{R}", "Chilling Screech", "{1}{U}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell or a Dragon spell, this creature gets +2/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), castFilter, false));

        // Chilling Screech
        // Counter target spell with mana value 2 or less.
        this.getSpellCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetSpell(filter));
        this.finalizeOmen();
    }

    private RunescaleStormbrood(final RunescaleStormbrood card) {
        super(card);
    }

    @Override
    public RunescaleStormbrood copy() {
        return new RunescaleStormbrood(this);
    }
}
