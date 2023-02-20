package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;


import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;



/**
 *
 * @author @stwalsh4118
 */
public final class MildManneredLibrarian extends CardImpl {

    public MildManneredLibrarian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{G}: Mild-Mannered Librarian becomes a Werewolf. Put two +1/+1 counters on it and you draw a card. Activate only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new MildManneredLibrarianEffect(), new ManaCostsImpl<>("{3}{G}"), TimingRule.INSTANT);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).setText("Put two +1/+1 counters on it"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).setText("and you draw a card"));
        this.addAbility(ability);
        
    }

    private MildManneredLibrarian(final MildManneredLibrarian card) {
        super(card);
    }

    @Override
    public MildManneredLibrarian copy() {
        return new MildManneredLibrarian(this);
    }
}

class MildManneredLibrarianEffect extends ContinuousEffectImpl {

    MildManneredLibrarianEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} becomes a Werewolf.";
    }

    private MildManneredLibrarianEffect(final MildManneredLibrarianEffect effect) {
        super(effect);
    }

    @Override
    public MildManneredLibrarianEffect copy() {
        return new MildManneredLibrarianEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCreatureTypes(game);
                permanent.addSubType(game, SubType.WEREWOLF);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
                return true;
        }
        return false;
    }
}
