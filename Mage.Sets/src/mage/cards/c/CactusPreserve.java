package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CommanderGreatestManaValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CactusPreserve extends CardImpl {

    public CactusPreserve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Cactus Preserve enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any type that a land you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, false));

        // {3}: Until end of turn, Cactus Preserve becomes an X/X green Plant creature with reach, where X is the greatest mana value among your commanders. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new CactusPreserveEffect(), new ManaCostsImpl<>("{3}")));
    }

    private CactusPreserve(final CactusPreserve card) {
        super(card);
    }

    @Override
    public CactusPreserve copy() {
        return new CactusPreserve(this);
    }
}

class CactusPreserveEffect extends OneShotEffect {

    CactusPreserveEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "{this} becomes an X/X green Plant creature with reach, where X is the greatest mana value among your commanders. It's still a land.";
    }

    private CactusPreserveEffect(final CactusPreserveEffect effect) {
        super(effect);
    }

    @Override
    public CactusPreserveEffect copy() {
        return new CactusPreserveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = CommanderGreatestManaValue.instance.calculate(game, source, this);
        game.addEffect(new BecomesCreatureSourceEffect(
                new CreatureToken(xValue, xValue,
                        "X/X green Plant creature with reach, where X is the greatest mana value among your commanders")
                        .withColor("G").withSubType(SubType.PLANT)
                        .withAbility(ReachAbility.getInstance()),
                CardType.LAND, Duration.EndOfTurn), source
        );
        return true;
    }
}
