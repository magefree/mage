package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GreenWhiteElfWarriorToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RhysTheRedeemed extends CardImpl {

    public RhysTheRedeemed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{GW}, {tap}: Create a 1/1 green and white Elf Warrior creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GreenWhiteElfWarriorToken()), new ManaCostsImpl("{2}{G/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {4}{GW}{GW}, {tap}: For each creature token you control, create a token that's a copy of that creature.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RhysTheRedeemedEffect(), new ManaCostsImpl("{4}{G/W}{G/W}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

    }

    private RhysTheRedeemed(final RhysTheRedeemed card) {
        super(card);
    }

    @Override
    public RhysTheRedeemed copy() {
        return new RhysTheRedeemed(this);
    }
}

class RhysTheRedeemedEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TokenPredicate.TRUE);
    }

    public RhysTheRedeemedEffect() {
        super(Outcome.Neutral);
        this.staticText = "For each creature token you control, create a token that's a copy of that creature";
    }

    public RhysTheRedeemedEffect(final RhysTheRedeemedEffect effect) {
        super(effect);
    }

    @Override
    public RhysTheRedeemedEffect copy() {
        return new RhysTheRedeemedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                if (permanent.isControlledBy(source.getControllerId())) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
