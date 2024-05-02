package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.MilledThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsMilledWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheMasterTranscendent extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card in a graveyard that was milled this turn");

    static {
        filter.add(MilledThisTurnPredicate.instance);
    }

    public TheMasterTranscendent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When The Master, Transcendent enters the battlefield, target player gets two rad counters.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.RAD.createInstance(2)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {T}: Put target creature card in a graveyard that was milled this turn onto the battlefield under your control. It's a green Mutant with base power and toughness 3/3.
        ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new TapSourceCost()
        );
        ability.addEffect(new TheMasterTranscendentContinuousEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability, new CardsMilledWatcher());
    }

    private TheMasterTranscendent(final TheMasterTranscendent card) {
        super(card);
    }

    @Override
    public TheMasterTranscendent copy() {
        return new TheMasterTranscendent(this);
    }
}

class TheMasterTranscendentContinuousEffect extends ContinuousEffectImpl {

    TheMasterTranscendentContinuousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "It's a green Mutant with base power and toughness 3/3";
        addDependencyType(DependencyType.AddingCreatureType);
    }

    protected TheMasterTranscendentContinuousEffect(final TheMasterTranscendentContinuousEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterTranscendentContinuousEffect copy() {
        return new TheMasterTranscendentContinuousEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.ColorChangingEffects_5
                || layer == Layer.PTChangingEffects_7;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        if (creature == null) {
            this.used = true;
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                creature.removeAllCreatureTypes(game);
                creature.addSubType(game, SubType.MUTANT);
                break;
            case ColorChangingEffects_5:
                creature.getColor(game).setColor(ObjectColor.GREEN);
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    creature.getPower().setModifiedBaseValue(3);
                    creature.getToughness().setModifiedBaseValue(3);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
