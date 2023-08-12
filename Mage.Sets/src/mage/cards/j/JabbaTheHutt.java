
package mage.cards.j;


import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HunterToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class JabbaTheHutt extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent controls with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public JabbaTheHutt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUTT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Put a bounty counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {R},{T}: Create a tapped 4/4 red Hunter creature token. It fights another target creature an opponent control with a bounty counter on it. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new JabbaTheHuttEffect(), new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private JabbaTheHutt(final JabbaTheHutt card) {
        super(card);
    }

    @Override
    public JabbaTheHutt copy() {
        return new JabbaTheHutt(this);
    }
}


class JabbaTheHuttEffect extends OneShotEffect {

    public JabbaTheHuttEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a tapped 4/4 red Hunter creature token. It fights another target creature an opponent control with a bounty counter on it";
    }

    public JabbaTheHuttEffect(final JabbaTheHuttEffect effect) {
        super(effect);
    }

    @Override
    public JabbaTheHuttEffect copy() {
        return new JabbaTheHuttEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new HunterToken(), 1, true, false);
            effect.apply(game, source);
            Permanent token = game.getPermanent(effect.getLastAddedTokenIds().get(0));
            Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (token != null && opponentCreature != null) {
                return token.fight(opponentCreature, source, game);
            }
        }
        return false;
    }
}
