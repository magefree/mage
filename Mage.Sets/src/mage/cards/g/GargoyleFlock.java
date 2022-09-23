package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.CreatureEnteredUnderYourControlCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.TyranidGargoyleToken;
import mage.watchers.common.CreatureEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GargoyleFlock extends CardImpl {

    public GargoyleFlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Skyswarm -- At the beginning of your end step, if a creature entered the battlefield under your control this turn, create a 1/1 blue Tyranid Gargoyle creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new TyranidGargoyleToken()), TargetController.YOU,
                CreatureEnteredUnderYourControlCondition.instance, false
        ).withFlavorWord("Skyswarm"), new CreatureEnteredControllerWatcher());
    }

    private GargoyleFlock(final GargoyleFlock card) {
        super(card);
    }

    @Override
    public GargoyleFlock copy() {
        return new GargoyleFlock(this);
    }
}
