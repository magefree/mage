package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.game.Game;

import java.util.UUID;
import mage.filter.predicate.ObjectSourcePlayerPredicate;

/**
 * @author TheElk801
 */
public final class DiamondKnight extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell of the chosen color");

    static {
        filter.add(DiamondKnightPredicate.instance);
    }

    public DiamondKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As Diamond Knight enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Whenever you cast a spell of the chosen color, put a +1/+1 counter on Diamond Knight.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false));
    }

    private DiamondKnight(final DiamondKnight card) {
        super(card);
    }

    @Override
    public DiamondKnight copy() {
        return new DiamondKnight(this);
    }
}

enum DiamondKnightPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        ObjectColor color = (ObjectColor) game.getState().getValue(input.getSourceId() + "_color");
        return input.getObject().getColor(game).shares(color);
    }

    @Override
    public String toString() {
        return "Chosen color";
    }
}
