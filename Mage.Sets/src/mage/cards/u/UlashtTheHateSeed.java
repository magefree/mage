
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class UlashtTheHateSeed extends CardImpl {

    public UlashtTheHateSeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HELLION);
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ulasht, the Hate Seed enters the battlefield with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control.
        this.addAbility(new EntersBattlefieldAbility(new UlashtTheHateSeedEffect(), "with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control."));

        // {1}, Remove a +1/+1 counter from Ulasht: Choose one - Ulasht deals 1 damage to target creature;
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        // or create a 1/1 green Saproling creature token.
        Effect effect = new CreateTokenEffect(new SaprolingToken());
        effect.setText("Create a 1/1 green Saproling creature token.");
        Mode mode = new Mode(effect);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private UlashtTheHateSeed(final UlashtTheHateSeed card) {
        super(card);
    }

    @Override
    public UlashtTheHateSeed copy() {
        return new UlashtTheHateSeed(this);
    }
}

class UlashtTheHateSeedEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent();
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent();

    static {
        filterGreen.add(AnotherPredicate.instance);
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterRed.add(AnotherPredicate.instance);
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    UlashtTheHateSeedEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with a +1/+1 counter on it for each other red creature you control and a +1/+1 counter on it for each other green creature you control.";
    }

    UlashtTheHateSeedEffect(final UlashtTheHateSeedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = game.getBattlefield().count(filterRed, source.getControllerId(), source, game);
            amount += game.getBattlefield().count(filterGreen, source.getControllerId(), source, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UlashtTheHateSeedEffect copy() {
        return new UlashtTheHateSeedEffect(this);
    }

}
