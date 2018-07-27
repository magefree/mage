package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author NinthWorld
 */
public final class TerranBanshee extends CardImpl {

    public TerranBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all damage Terran Banshee would deal to creatures with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TerranBansheeEffect()));

        // Morph {2}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{R}")));

    }

    public TerranBanshee(final TerranBanshee card) {
        super(card);
    }

    @Override
    public TerranBanshee copy() {
        return new TerranBanshee(this);
    }
}

class TerranBansheeEffect extends PreventionEffectImpl {

    public TerranBansheeEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage {this} would deal to creatures with flying";
    }

    public TerranBansheeEffect(final TerranBansheeEffect effect) {
        super(effect);
    }

    @Override
    public TerranBansheeEffect copy() {
        return new TerranBansheeEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if(damaged != null) {
                return event.getSourceId().equals(source.getSourceId())
                        && damaged.isCreature()
                        && damaged.hasAbility(FlyingAbility.getInstance().getId(), game);
            }
        }
        return false;
    }
}
