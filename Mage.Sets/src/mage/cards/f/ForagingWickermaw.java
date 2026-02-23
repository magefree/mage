package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ForagingWickermaw extends CardImpl {

    public ForagingWickermaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {1}: Add one mana of any color. This creature becomes that color until end of turn. Activate only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD,
                new ForagingWickermawManaEffect(),
                new GenericManaCost(1)
        ));

    }

    private ForagingWickermaw(final ForagingWickermaw card) {
        super(card);
    }

    @Override
    public ForagingWickermaw copy() {
        return new ForagingWickermaw(this);
    }
}

class ForagingWickermawManaEffect extends AddManaOfAnyColorEffect {

    ForagingWickermawManaEffect() {
        super(1);
        this.staticText = "Add one mana of any color. {this} becomes that color until end of turn";
    }

    private ForagingWickermawManaEffect(final ForagingWickermawManaEffect effect) {
        super(effect);
    }

    @Override
    public ForagingWickermawManaEffect copy() {
        return new ForagingWickermawManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                String mes = String.format("Select a color of mana to add %d of it", this.amount);
                ChoiceColor choice = new ChoiceColor(true, mes, game.getObject(source));
                if (controller.choose(outcome, choice, game)) {
                    ObjectColor color = choice.getColor();
                    if (color != null) {
                        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
                        if (permanent != null) {
                            game.addEffect(new BecomesColorTargetEffect(color, false, Duration.EndOfTurn)
                                    .setTargetPointer(new FixedTarget(permanent, game)), source);
                        }
                        Mana mana = choice.getMana(amount);
                        mana.setFlag(setFlag);
                        return mana;
                    }
                }
            }
        }
        return new Mana();
    }

}
