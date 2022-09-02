package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SatyrCantBlockToken;

import java.util.UUID;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 * @author TheElk801
 */
public final class AnaxHardenedInTheForge extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public AnaxHardenedInTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMIGOD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Anax's power is equal to your devotion to red.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerSourceEffect(DevotionCount.R, Duration.EndOfGame)
                        .setText("{this}'s power is equal to your devotion to red")
        ).addHint(DevotionCount.R.getHint()));

        // Whenever Anax or another nontoken creature you control dies, create a 1/1 red Satyr creature token 
        // with "This creature can't block." If the creature had power 4 or greater, create two of those tokens instead.
        this.addAbility(new AnaxHardenedInTheForgeTriggeredAbility(null, false, filter));
    }

    private AnaxHardenedInTheForge(final AnaxHardenedInTheForge card) {
        super(card);
    }

    @Override
    public AnaxHardenedInTheForge copy() {
        return new AnaxHardenedInTheForge(this);
    }
}

class AnaxHardenedInTheForgeTriggeredAbility extends DiesThisOrAnotherCreatureTriggeredAbility {

    AnaxHardenedInTheForgeTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(effect, optional, filter);
    }

    private AnaxHardenedInTheForgeTriggeredAbility(final AnaxHardenedInTheForgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnaxHardenedInTheForgeTriggeredAbility copy() {
        return new AnaxHardenedInTheForgeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            int tokenCount = ((ZoneChangeEvent) event).getTarget().getPower().getValue() > 3 ? 2 : 1;
            this.getEffects().clear();
            this.addEffect(new CreateTokenEffect(new SatyrCantBlockToken(), tokenCount));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nontoken creature you control dies, "
                + "create a 1/1 red Satyr creature token with \"This creature can't block.\" "
                + "If the creature had power 4 or greater, create two of those tokens instead.";
    }
}
