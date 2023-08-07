package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.OviyaPashiriSageLifecrafterToken;
import mage.game.permanent.token.ServoToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OviyaPashiriSageLifecrafter extends CardImpl {

    public OviyaPashiriSageLifecrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{G}, {T}: Create a 1/1 colorless Servo artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ServoToken(), 1), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {4}{G}, {T}: Create an X/X colorless Construct artifact creature token, where X is the number of creatures you control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OviyaPashiriSageLifecrafterEffect(), new ManaCostsImpl<>("{4}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addHint(CreaturesYouControlHint.instance);
        this.addAbility(ability);
    }

    private OviyaPashiriSageLifecrafter(final OviyaPashiriSageLifecrafter card) {
        super(card);
    }

    @Override
    public OviyaPashiriSageLifecrafter copy() {
        return new OviyaPashiriSageLifecrafter(this);
    }
}

class OviyaPashiriSageLifecrafterEffect extends OneShotEffect {

    public OviyaPashiriSageLifecrafterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create an X/X colorless Construct artifact creature token, where X is the number of creatures you control";
    }

    public OviyaPashiriSageLifecrafterEffect(final OviyaPashiriSageLifecrafterEffect effect) {
        super(effect);
    }

    @Override
    public OviyaPashiriSageLifecrafterEffect copy() {
        return new OviyaPashiriSageLifecrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
            return new CreateTokenEffect(new OviyaPashiriSageLifecrafterToken(creatures)).apply(game, source);
        }
        return false;
    }
}
