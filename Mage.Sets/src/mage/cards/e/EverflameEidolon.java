
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class EverflameEidolon extends CardImpl {

    public EverflameEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bestow {2}{R}
        this.addAbility(new BestowAbility(this, "{2}{R}"));
        // {R}: Everflame Eidolon gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new EverflameEidolonEffect(), new ManaCostsImpl<>("{R}")));
        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private EverflameEidolon(final EverflameEidolon card) {
        super(card);
    }

    @Override
    public EverflameEidolon copy() {
        return new EverflameEidolon(this);
    }
}

class EverflameEidolonEffect extends OneShotEffect {

    public EverflameEidolonEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/+0 until end of turn. If it's an Aura, enchanted creature gets +1/+0 until end of turn instead";
    }

    public EverflameEidolonEffect(final EverflameEidolonEffect effect) {
        super(effect);
    }

    @Override
    public EverflameEidolonEffect copy() {
        return new EverflameEidolonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            if (sourceObject.hasSubtype(SubType.AURA, game)) {
                game.addEffect(new BoostEnchantedEffect(1, 0, Duration.EndOfTurn), source);
            } else {
                game.addEffect(new BoostSourceEffect(1, 0, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
