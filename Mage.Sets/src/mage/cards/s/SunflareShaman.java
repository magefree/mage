
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class SunflareShaman extends CardImpl {

    public SunflareShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{R}, {tap}: Sunflare Shaman deals X damage to any target and X damage to itself, where X is the number of Elemental cards in your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SunflareShamanEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private SunflareShaman(final SunflareShaman card) {
        super(card);
    }

    @Override
    public SunflareShaman copy() {
        return new SunflareShaman(this);
    }
}

class SunflareShamanEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Elemental");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public SunflareShamanEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to any target and X damage to itself, where X is the number of Elemental cards in your graveyard";
    }

    public SunflareShamanEffect(final SunflareShamanEffect effect) {
        super(effect);
    }

    @Override
    public SunflareShamanEffect copy() {
        return new SunflareShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int ElementalsInYourGraveyard = controller.getGraveyard().count(filter, game);
            new DamageTargetEffect(ElementalsInYourGraveyard).apply(game, source);
            new DamageSelfEffect(ElementalsInYourGraveyard).apply(game, source);
            return true;
        }
        return false;
    }
}
