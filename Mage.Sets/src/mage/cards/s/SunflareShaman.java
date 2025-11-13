package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetAndSelfEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

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
        Ability ability = new SimpleActivatedAbility(new SunflareShamanEffect(), new ManaCostsImpl<>("{1}{R}"));
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

    SunflareShamanEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to any target and X damage to itself, where X is the number of Elemental cards in your graveyard";
    }

    private SunflareShamanEffect(final SunflareShamanEffect effect) {
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
            int amount = controller.getGraveyard().count(filter, game);
            new DamageTargetAndSelfEffect(amount, amount).apply(game, source);
            return true;
        }
        return false;
    }
}
