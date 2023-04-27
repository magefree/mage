package mage.cards.c;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreaturePutInYourGraveyardCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SquirrelToken;
import mage.watchers.common.CreaturePutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloakwoodHermit extends CardImpl {

    public CloakwoodHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "At the beginning of your end step, if a creature card was put into your graveyard from anywhere this turn, create two tapped 1/1 green Squirrel creature tokens."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new SquirrelToken(), 2, true, false),
                        TargetController.YOU, CreaturePutInYourGraveyardCondition.instance, false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).withForceQuotes()).addHint(CreaturePutInYourGraveyardCondition.getHint()), new CreaturePutIntoGraveyardWatcher());
    }

    private CloakwoodHermit(final CloakwoodHermit card) {
        super(card);
    }

    @Override
    public CloakwoodHermit copy() {
        return new CloakwoodHermit(this);
    }
}
