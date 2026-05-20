package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class WookieeMystic extends CardImpl {

    public WookieeMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {R}, {G} or {W}. If that mana is spent on a creature spell, it enters with a +1/+1 counter on it.
        Mana mana = Mana.RedMana(1);
        mana.setFlag(true);
        ManaEffect effect = new BasicManaEffect(mana);
        effect.setText("Add {R}. If that mana is spent on a creature spell, it enters with a +1/+1 counter on it");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher());

        mana = Mana.GreenMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {G}. If that mana is spent on a creature spell, it enters with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher());

        mana = Mana.WhiteMana(1);
        mana.setFlag(true);
        effect = new BasicManaEffect(mana);
        effect.setText("Add {W}. If that mana is spent on a creature spell, it enters with a +1/+1 counter on it");
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability, new WookieeMysticWatcher());
    }

    private WookieeMystic(final WookieeMystic card) {
        super(card);
    }

    @Override
    public WookieeMystic copy() {
        return new WookieeMystic(this);
    }
}

class WookieeMysticWatcher extends Watcher {

    WookieeMysticWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            Spell target = game.getSpell(event.getTargetId());
            if (event.getSourceId() != null
                    && event.getSourceId().equals(this.getSourceId())
                    && target != null && target.isCreature(game)
                    && event.getFlag()) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game)),
                        target.getSpellAbility());
            }
        }
    }

}
