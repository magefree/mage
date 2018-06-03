
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx
 *
 */
public final class DarkImpostor extends CardImpl {

    public DarkImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{B}{B}: Exile target creature and put a +1/+1 counter on Dark Impostor.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DarkImpostorExileTargetEffect(), new ManaCostsImpl("{4}{B}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Dark Impostor has all activated abilities of all creature cards exiled with it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DarkImpostorContinuousEffect()));
    }

    public DarkImpostor(final DarkImpostor card) {
        super(card);
    }

    @Override
    public DarkImpostor copy() {
        return new DarkImpostor(this);
    }
}

class DarkImpostorExileTargetEffect extends OneShotEffect {

    public DarkImpostorExileTargetEffect() {
        super(Outcome.Exile);
    }

    public DarkImpostorExileTargetEffect(final DarkImpostorExileTargetEffect effect) {
        super(effect);
    }

    @Override
    public DarkImpostorExileTargetEffect copy() {
        return new DarkImpostorExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent != null) {
            permanent.moveToExile(null, null, source.getSourceId(), game);
            if (sourceObject instanceof Permanent) {
                ((Permanent) sourceObject).imprint(permanent.getId(), game);
            }
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
    }

    @Override
    public String getText(Mode mode) {
        return "exile target creature and put a +1/+1 counter on {this}";
    }
}

class DarkImpostorContinuousEffect extends ContinuousEffectImpl {

    public DarkImpostorContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creature cards exiled with it";
    }

    public DarkImpostorContinuousEffect(final DarkImpostorContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            for (UUID imprintedId : perm.getImprinted()) {
                Card card = game.getCard(imprintedId);
                if (card != null) {
                    for (Ability ability : card.getAbilities()) {
                        if (ability instanceof ActivatedAbility) {
                            perm.addAbility(ability.copy(), source.getSourceId(), game);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public DarkImpostorContinuousEffect copy() {
        return new DarkImpostorContinuousEffect(this);
    }

}
