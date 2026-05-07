package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.ContractToken;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ScrivTheObligator extends CardImpl {

    public ScrivTheObligator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INKLING);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Scriv enters or attacks, create a white Aura enchantment token named Contract attached to target creature an opponent controls.
        // The token has enchant creature and "Whenever enchanted creature attacks, it gets +2/+0 until end of turn if it's attacking one of your opponents. Otherwise, its controller loses 2 life."
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ScrivTheObligatorEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ScrivTheObligator(final ScrivTheObligator card) {
        super(card);
    }

    @Override
    public ScrivTheObligator copy() {
        return new ScrivTheObligator(this);
    }
}

class ScrivTheObligatorEffect extends OneShotEffect {

    ScrivTheObligatorEffect() {
        super(Outcome.Benefit);
        staticText = "create a white Aura enchantment token named Contract attached to target creature an opponent controls. The token has enchant creature and \"Whenever enchanted creature attacks, it gets +2/+0 until end of turn if it's attacking one of your opponents. Otherwise, its controller loses 2 life.\"";
    }

    private ScrivTheObligatorEffect(final ScrivTheObligatorEffect effect) {
        super(effect);
    }

    @Override
    public ScrivTheObligatorEffect copy() {
        return new ScrivTheObligatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        new ContractToken().putOntoBattlefield(1, game, source, source.getControllerId(), false, false, null, targetId);

        return true;
    }
}
