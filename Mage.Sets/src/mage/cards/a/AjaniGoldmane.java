
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AjaniGoldmane extends CardImpl {

    public AjaniGoldmane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(4);

        // +1: You gain 2 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(2), 1));

        // -1: Put a +1/+1 counter on each creature you control. Those creatures gain vigilance until end of turn.
        Effects effects1 = new Effects();
        effects1.add(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()));
        effects1.add(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent()));
        this.addAbility(new LoyaltyAbility(effects1, -1));

        // -6: Create a white Avatar creature token. It has "This creature's power and toughness are each equal to your life total."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AvatarToken()), -6));

    }

    private AjaniGoldmane(final AjaniGoldmane card) {
        super(card);
    }

    @Override
    public AjaniGoldmane copy() {
        return new AjaniGoldmane(this);
    }

}

class AvatarToken extends TokenImpl {

    public AvatarToken() {
        super("Avatar", "white Avatar creature token with \"This creature's power and toughness are each equal to your life total.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AVATAR);
        color.setWhite(true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AvatarTokenEffect()));
    }
    public AvatarToken(final AvatarToken token) {
        super(token);
    }

    public AvatarToken copy() {
        return new AvatarToken(this);
    }
}

class AvatarTokenEffect extends ContinuousEffectImpl {

    public AvatarTokenEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
    }

    public AvatarTokenEffect(final AvatarTokenEffect effect) {
        super(effect);
    }

    @Override
    public AvatarTokenEffect copy() {
        return new AvatarTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent token = game.getPermanent(source.getSourceId());
        if (token != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                token.getPower().setValue(controller.getLife());
                token.getToughness().setValue(controller.getLife());
                return true;
            }
        }
        return false;
    }

}
