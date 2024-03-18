package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SharpEyedRookie extends CardImpl {

    public SharpEyedRookie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature enters the battlefield under your control, if its power is greater than Sharp-Eyed Rookie's power or its toughness is greater than Sharp-Eyed Rookie's toughness, put a +1/+1 counter on Sharp-Eyed Rookie and investigate.
        this.addAbility(new SharpEyedRookieTriggeredAbility());
    }

    private SharpEyedRookie(final SharpEyedRookie card) {
        super(card);
    }

    @Override
    public SharpEyedRookie copy() {
        return new SharpEyedRookie(this);
    }
}

class SharpEyedRookieTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    SharpEyedRookieTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_CONTROLLED_CREATURE, false);
        this.addEffect(new InvestigateEffect().concatBy("and"));
        setTriggerPhrase("Whenever a creature enters the battlefield under your control, " +
                "if its power is greater than {this}'s power or its toughness is greater than {this}'s toughness, ");
    }

    private SharpEyedRookieTriggeredAbility(final SharpEyedRookieTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SharpEyedRookieTriggeredAbility copy() {
        return new SharpEyedRookieTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        Permanent permanentEntering = (Permanent) this
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("permanentEnteringBattlefield"))
                .findFirst()
                .orElse(null);
        return sourcePermanent != null
                && permanentEntering != null
                && sourcePermanent.isCreature(game)
                && permanentEntering.isCreature(game)
                && (permanentEntering.getPower().getValue() > sourcePermanent.getPower().getValue()
                || permanentEntering.getToughness().getValue() > sourcePermanent.getToughness().getValue());
    }
}
