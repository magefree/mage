package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 * @author balazskristof
 */
public final class CecilDarkKnight extends CardImpl {

    public CecilDarkKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.c.CecilRedeemedPaladin.class;

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Darkness -- Whenever Cecil deals damage, you lose that much life. Then if your life total is less than or equal to half your starting life total, untap Cecil and transform it.
        this.addAbility(new TransformAbility());
        this.addAbility(new DealsDamageSourceTriggeredAbility(new CecilDarkKnightEffect()).withFlavorWord("Darkness"));
    }

    private CecilDarkKnight(final CecilDarkKnight card) {
        super(card);
    }

    @Override
    public CecilDarkKnight copy() {
        return new CecilDarkKnight(this);
    }
}

class CecilDarkKnightEffect extends OneShotEffect {

    CecilDarkKnightEffect() {
        super(Outcome.LoseLife);
        staticText = "you lose that much life. Then if your life total is less than or equal to half your starting life total, untap {this} and transform it.";
    }

    private CecilDarkKnightEffect(final CecilDarkKnightEffect effect) {
        super(effect);
    }

    @Override
    public CecilDarkKnightEffect copy() {
        return new CecilDarkKnightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.processAction();
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.loseLife(SavedDamageValue.MUCH.calculate(game, source, this), game, source, false);
            game.processAction();
            if (player.getLife() <= game.getStartingLife() / 2) {
                new UntapSourceEffect().apply(game, source);
                new TransformSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

}