
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HomuraHumanAscendant extends CardImpl {

    public HomuraHumanAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.flipCard = true;
        this.flipCardName = "Homura's Essence";

        // Homura, Human Ascendant can't block.
        this.addAbility(new CantBlockAbility());
        // When Homura dies, return it to the battlefield flipped.
        this.addAbility(new DiesSourceTriggeredAbility(new HomuraReturnFlippedSourceEffect(new HomurasEssence2())));
    }

    private HomuraHumanAscendant(final HomuraHumanAscendant card) {
        super(card);
    }

    @Override
    public HomuraHumanAscendant copy() {
        return new HomuraHumanAscendant(this);
    }
}

class HomuraReturnFlippedSourceEffect extends OneShotEffect {

    private final Token flipToken;

    public HomuraReturnFlippedSourceEffect(Token flipToken) {
        super(Outcome.BecomeCreature);
        this.flipToken = flipToken;
        staticText = "return it to the battlefield flipped";
    }

    public HomuraReturnFlippedSourceEffect(final HomuraReturnFlippedSourceEffect effect) {
        super(effect);
        this.flipToken = effect.flipToken;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard != null && controller != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            ContinuousEffect effect = new ConditionalContinuousEffect(new CopyTokenEffect(flipToken), FlippedCondition.instance, "");
            game.addEffect(effect, source);
            controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.flip(game);  // not complete correct because it should enter the battlefield flipped
            }
            return true;
        }
        return false;
    }

    @Override
    public HomuraReturnFlippedSourceEffect copy() {
        return new HomuraReturnFlippedSourceEffect(this);
    }

}

class HomurasEssence2 extends TokenImpl {

    HomurasEssence2() {
        super("Homura's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);
        color.setRed(true);
        // Creatures you control get +2/+2 and have flying and "{R}: This creature gets +1/+0 until end of turn."
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and have flying");
        ability.addEffect(effect);
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        effect = new GainAbilityControlledEffect(gainedAbility, Duration.WhileOnBattlefield, filter);
        effect.setText("and \"{R}: This creature gets +1/+0 until end of turn.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }
    public HomurasEssence2(final HomurasEssence2 token) {
        super(token);
    }

    public HomurasEssence2 copy() {
        return new HomurasEssence2(this);
    }
}
