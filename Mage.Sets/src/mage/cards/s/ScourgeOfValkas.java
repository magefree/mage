package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ScourgeOfValkas extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragon");

    public ScourgeOfValkas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scourge of Valkas or another Dragon enters the battlefield under your control, it deals X damage to any target, where X is the number of Dragons you control.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ScourgeOfValkasDamageEffect(), filter, false, true
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {R}: Scourge of Valkas gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private ScourgeOfValkas(final ScourgeOfValkas card) {
        super(card);
    }

    @Override
    public ScourgeOfValkas copy() {
        return new ScourgeOfValkas(this);
    }
}

class ScourgeOfValkasDamageEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON, "");

    ScourgeOfValkasDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals X damage to any target, where X is the number of Dragons you control";
    }

    private ScourgeOfValkasDamageEffect(final ScourgeOfValkasDamageEffect effect) {
        super(effect);
    }

    @Override
    public ScourgeOfValkasDamageEffect copy() {
        return new ScourgeOfValkasDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enteringDragon = (Permanent) getValue("permanentEnteringBattlefield");
        if (controller == null || enteringDragon == null) {
            return false;
        }
        int dragons = game.getBattlefield().count(filter, source.getControllerId(), source, game);
        if (dragons < 1) {
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(dragons, enteringDragon.getId(), source, game, false, true);
        } else {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                player.damage(dragons, enteringDragon.getId(), source, game);
            }
        }
        return true;
    }
}
