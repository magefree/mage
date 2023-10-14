package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.HydraBroodmasterToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HydraBroodmaster extends CardImpl {

    public HydraBroodmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // {X}{X}{G}: Monstrosity X
        this.addAbility(new MonstrosityAbility("{X}{X}{G}", Integer.MAX_VALUE));

        // When Hydra Broodmaster becomes monstrous, create X X/X green Hydra creature tokens.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new HydraBroodmasterEffect()));
    }

    private HydraBroodmaster(final HydraBroodmaster card) {
        super(card);
    }

    @Override
    public HydraBroodmaster copy() {
        return new HydraBroodmaster(this);
    }
}

class HydraBroodmasterEffect extends OneShotEffect {

    public HydraBroodmasterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X X/X green Hydra creature tokens";
    }

    private HydraBroodmasterEffect(final HydraBroodmasterEffect effect) {
        super(effect);
    }

    @Override
    public HydraBroodmasterEffect copy() {
        return new HydraBroodmasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = ((BecomesMonstrousSourceTriggeredAbility) source).getMonstrosityValue();
            return new CreateTokenEffect(new HydraBroodmasterToken(xValue, xValue), xValue).apply(game, source);
        }
        return false;
    }
}
