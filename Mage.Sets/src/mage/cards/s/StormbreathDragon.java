package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormbreathDragon extends CardImpl {

    public StormbreathDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // {5}{R}{R}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{R}{R}", 3));
        // When Stormbreath Dragon becomes monstrous, it deals damage to each opponent equal to the number of cards in that player's hand.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new StormbreathDragonDamageEffect()));
    }

    private StormbreathDragon(final StormbreathDragon card) {
        super(card);
    }

    @Override
    public StormbreathDragon copy() {
        return new StormbreathDragon(this);
    }
}

class StormbreathDragonDamageEffect extends OneShotEffect {

    public StormbreathDragonDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals damage to each opponent equal to the number of cards in that player's hand";
    }

    private StormbreathDragonDamageEffect(final StormbreathDragonDamageEffect effect) {
        super(effect);
    }

    @Override
    public StormbreathDragonDamageEffect copy() {
        return new StormbreathDragonDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                int damage = opponent.getHand().size();
                if (damage > 0) {
                    opponent.damage(damage, source.getSourceId(), source, game);
                }
            }
        }
        return true;
    }
}