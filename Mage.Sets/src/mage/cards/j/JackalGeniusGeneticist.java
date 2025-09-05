package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.RemoveTypeCopyApplier;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class JackalGeniusGeneticist extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("a creature spell with mana value equal to {this}'s power");

    static {
        filter.add(JackalGeniusGeneticistPredicate.instance);
    }

    public JackalGeniusGeneticist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast a creature spell with mana value equal to Jackal's power, copy that spell, except the copy isn't legendary. Then put a +1/+1 counter on Jackal.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CopyTargetStackObjectEffect(false, false, false, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY))
                        .setText("copy that spell, except the copy isn't legendary."),
                filter,
                false,
                SetTargetPointer.SPELL
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .concatBy("Then"));
        this.addAbility(ability);
    }

    private JackalGeniusGeneticist(final JackalGeniusGeneticist card) {
        super(card);
    }

    @Override
    public JackalGeniusGeneticist copy() {
        return new JackalGeniusGeneticist(this);
    }
}

enum JackalGeniusGeneticistPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = input.getSource().getSourcePermanentOrLKI(game);
        return sourcePermanent != null && input.getObject().getManaValue() == sourcePermanent.getPower().getValue();
    }

    @Override
    public String toString() {
        return "mana value equal to {this}'s power";
    }
}

