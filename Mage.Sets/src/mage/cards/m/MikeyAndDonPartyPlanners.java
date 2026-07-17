package mage.cards.m;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MikeyAndDonPartyPlanners extends CardImpl {

    private static final FilterCard filter = new FilterCard("play lands and cast Mutant, Ninja, or Turtle spells");

    static {
        filter.add(Predicates.or(
            CardType.LAND.getPredicate(),
            SubType.MUTANT.getPredicate(),
            SubType.NINJA.getPredicate(),
            SubType.TURTLE.getPredicate()
        ));
    }

    public MikeyAndDonPartyPlanners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play lands and cast Mutant, Ninja, or Turtle spells from the top of your library. If you cast a creature spell this way, that creature enters with an additional +1/+1 counter on it.
        Effect effect = new PlayFromTopOfLibraryEffect(filter);
        effect.setText(effect.getText(null) + ". If you cast a creature spell this way, that creature enters with an additional +1/+1 counter on it");
        Ability ability = new SimpleStaticAbility(effect);
        ability.setIdentifier(MageIdentifier.MikeyAndDonWatcher);
        ability.addWatcher(new MikeyAndDonWatcher());
        this.addAbility(ability);
    }

    private MikeyAndDonPartyPlanners(final MikeyAndDonPartyPlanners card) {
        super(card);
    }

    @Override
    public MikeyAndDonPartyPlanners copy() {
        return new MikeyAndDonPartyPlanners(this);
    }
}

class MikeyAndDonWatcher extends Watcher {

    MikeyAndDonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.MikeyAndDonWatcher)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game)),
                    target.getSpellAbility());
            }
        }
    }
}
