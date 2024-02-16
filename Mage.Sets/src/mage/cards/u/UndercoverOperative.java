package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
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
import mage.game.permanent.Permanent;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercoverOperative extends CardImpl {

    public UndercoverOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Undercover Operative enter the battlefield as a copy of any creature on the battlefield, except it enters with a shield counter on it if you control that creature.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, new UndercoverOperativeApplier()
        ), true));
    }

    private UndercoverOperative(final UndercoverOperative card) {
        super(card);
    }

    @Override
    public UndercoverOperative copy() {
        return new UndercoverOperative(this);
    }
}

class UndercoverOperativeApplier extends CopyApplier {

    @Override
    public String getText() {
        return ", except it enters with a shield counter on it if you control that creature";
    }

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        if (!isCopyOfCopy(source, blueprint, copyToObjectId)
                && ((Permanent) blueprint).isControlledBy(source.getControllerId())) {
            blueprint.getAbilities().add(new EntersBattlefieldAbility(
                    new AddCountersSourceEffect(CounterType.SHIELD.createInstance(), false)
                            .setText("with a shield counter on it")
            ));
        }
        return true;
    }
}
