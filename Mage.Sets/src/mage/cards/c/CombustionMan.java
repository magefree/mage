package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombustionMan extends CardImpl {

    public CombustionMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever Combustion Man attacks, destroy target permanent unless its controller has Combustion Man deal damage to them equal to his power.
        Ability ability = new AttacksTriggeredAbility(new CombustionManEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private CombustionMan(final CombustionMan card) {
        super(card);
    }

    @Override
    public CombustionMan copy() {
        return new CombustionMan(this);
    }
}

class CombustionManEffect extends OneShotEffect {

    CombustionManEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target permanent unless its controller has {this} deal damage to them equal to his power";
    }

    private CombustionManEffect(final CombustionManEffect effect) {
        super(effect);
    }

    @Override
    public CombustionManEffect copy() {
        return new CombustionManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null || !player.chooseUse(
                Outcome.Damage, "Have " + CardUtil.getSourceIdName(game, source) +
                        " deal damage to you equal to its power?", "If you don't, " +
                        permanent.getIdName() + " will be destroyed",
                "Take damage", "Destroy permanent", source, game
        )) {
            return permanent.destroy(source, game);
        }
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(x -> x > 0)
                .filter(x -> player.damage(x, source, game) > 0)
                .isPresent();
    }
}
