package mage.cards.t;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ThundermaneDragon extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("cast creature spells with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public ThundermaneDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells with power 4 or greater from the top of your library. If you cast a creature spell this way, it gains haste until end of turn.
        Effect effect = new PlayFromTopOfLibraryEffect(filter);
        effect.setText(effect.getText(null) + ". If you cast a creature spell this way, it gains haste until end of turn");
        Ability ability = new SimpleStaticAbility(effect);
        ability.setIdentifier(MageIdentifier.ThundermanDragonWatcher);
        ability.addWatcher(new ThundermaneDragonWatcher());
        this.addAbility(ability);
    }

    private ThundermaneDragon(final ThundermaneDragon card) {
        super(card);
    }

    @Override
    public ThundermaneDragon copy() {
        return new ThundermaneDragon(this);
    }
}

class ThundermaneDragonWatcher extends Watcher {

    ThundermaneDragonWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.ThundermanDragonWatcher)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance());
                effect.setTargetPointer(new FixedTarget(target.getCard().getId()));
                game.getState().addEffect(effect, target.getSpellAbility());
            }
        }
    }
}
