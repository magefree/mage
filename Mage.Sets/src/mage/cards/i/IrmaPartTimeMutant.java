package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author anonymous
 */
public final class IrmaPartTimeMutant extends CardImpl {

    public IrmaPartTimeMutant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, Irma becomes a copy of up to one other target creature you control,
        // except her name is Irma, Part-Time Mutant and she has this ability. Then put a +1/+1 counter on her.
        Ability ability = new BeginningOfCombatTriggeredAbility(new IrmaPartTimeMutantEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("Then put a +1/+1 counter on her"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private IrmaPartTimeMutant(final IrmaPartTimeMutant card) {
        super(card);
    }

    @Override
    public IrmaPartTimeMutant copy() {
        return new IrmaPartTimeMutant(this);
    }
}

class IrmaPartTimeMutantEffect extends OneShotEffect {

    IrmaPartTimeMutantEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of up to one other target creature you control, "
                + "except her name is Irma, Part-Time Mutant and she has this ability.";
    }

    private IrmaPartTimeMutantEffect(final IrmaPartTimeMutantEffect effect) {
        super(effect);
    }

    @Override
    public IrmaPartTimeMutantEffect copy() {
        return new IrmaPartTimeMutantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent irmaPartTimeMutant = game.getPermanent(source.getSourceId());
        if (irmaPartTimeMutant == null) {
            return false;
        }
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (copyFromPermanent != null) {
            game.copyPermanent(copyFromPermanent, irmaPartTimeMutant.getId(), source, new IrmaPartTimeMutantCopyApplier());
        }
        return true;
    }
}

class IrmaPartTimeMutantCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        Ability ability = new BeginningOfCombatTriggeredAbility(new IrmaPartTimeMutantEffect());
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("Then put a +1/+1 counter on her"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        blueprint.getAbilities().add(ability);
        blueprint.setName("Irma, Part-Time Mutant");
        return true;
    }
}
