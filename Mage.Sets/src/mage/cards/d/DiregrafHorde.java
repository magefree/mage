package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiregrafHorde extends CardImpl {

    public DiregrafHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Diregraf Horde enters the battlefield, create two 2/2 black Zombie creature tokens with decayed. When you do, exile up to two target cards from graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiregrafHordeEffect()));
    }

    private DiregrafHorde(final DiregrafHorde card) {
        super(card);
    }

    @Override
    public DiregrafHorde copy() {
        return new DiregrafHorde(this);
    }
}

class DiregrafHordeEffect extends OneShotEffect {

    DiregrafHordeEffect() {
        super(Outcome.Benefit);
        staticText = "create two 2/2 black Zombie creature tokens with decayed. " +
                "When you do, exile up to two target cards from graveyards";
    }

    private DiregrafHordeEffect(final DiregrafHordeEffect effect) {
        super(effect);
    }

    @Override
    public DiregrafHordeEffect copy() {
        return new DiregrafHordeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!new ZombieDecayedToken().putOntoBattlefield(2, game, source, source.getControllerId())) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ExileTargetEffect(), false, "exile up to two target cards from graveyards"
        );
        ability.addTarget(new TargetCardInGraveyard(0, 2));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
