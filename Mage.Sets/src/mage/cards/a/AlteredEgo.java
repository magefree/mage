package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AlteredEgo extends CardImpl {

    public AlteredEgo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{2}{G}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Altered Ego can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // You may have Altered Ego enter the battlefield as a copy of any creature on the battlefield, except it enters with an additional X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new AlteredEgoCopyApplier()),
                true
        ));
    }

    private AlteredEgo(final AlteredEgo card) {
        super(card);
    }

    @Override
    public AlteredEgo copy() {
        return new AlteredEgo(this);
    }
}

class AlteredEgoCopyApplier extends CopyApplier {

    @Override
    public String getText() {
        return ", except it enters with an additional X +1/+1 counters on it";
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        // counters only for original card, not copies, see rules:
        // 706.9e
        // Some replacement effects that generate copy effects include an exception that’s an additional
        // effect rather than a modification of the affected object’s characteristics. If another copy
        // effect is applied to that object after applying the copy effect with that exception, the
        // exception’s effect doesn’t happen.

        if (!isCopyOfCopy(source, blueprint, copyToObjectId) && CardUtil.checkSourceCostsTagExists(game, source, "X")) {
            // except it enters with an additional X +1/+1 counters on it
            blueprint.getAbilities().add(
                    new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(
                            CardUtil.getSourceCostsTag(game, source, "X", 0)
                    )))
            );

            /*
             * If the chosen creature has {X} in its mana cost, that X is considered to be 0.
             * The value of X in Altered Ego's last ability will be whatever value was chosen for X while casting Altered Ego.
             * (2016-04-08)
             * So the X value of Altered Ego must be separate from the copied creature's X value
             */
            CardUtil.getSourceCostsTagsMap(game, source).remove("X");
        }

        return true;
    }
}