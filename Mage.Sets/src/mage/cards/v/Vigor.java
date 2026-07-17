package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Vigor extends CardImpl {

    public Vigor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If damage would be dealt to a creature you control other than Vigor, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(new VigorEffect()));

        // When Vigor is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private Vigor(final Vigor card) {
        super(card);
    }

    @Override
    public Vigor copy() {
        return new Vigor(this);
    }
}

class VigorEffect extends PreventionEffectImpl {

    VigorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "if damage would be dealt to another creature you control, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way";

    }

    private VigorEffect(final VigorEffect effect) {
        super(effect);
    }

    @Override
    public VigorEffect copy() {
        return new VigorEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(preventionEffectData.getPreventedDamage()), source.getControllerId(), source, game);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && !event.getTargetId().equals(source.getSourceId());
    }

}
