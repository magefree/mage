package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class SaffiEriksdotter extends CardImpl {

    public SaffiEriksdotter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Saffi Eriksdotter: When target creature is put into your graveyard from the battlefield this turn, return that card to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new SaffiEriksdotterDelayedTriggeredAbility()),
                new SacrificeSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SaffiEriksdotter(final SaffiEriksdotter card) {
        super(card);
    }

    @Override
    public SaffiEriksdotter copy() {
        return new SaffiEriksdotter(this);
    }
}

class SaffiEriksdotterDelayedTriggeredAbility extends WhenTargetDiesDelayedTriggeredAbility {

    public SaffiEriksdotterDelayedTriggeredAbility() {
        super(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("return that card to the battlefield"), SetTargetPointer.CARD);
        setTriggerPhrase("When target creature is put into your graveyard from the battlefield this turn, ");
    }

    private SaffiEriksdotterDelayedTriggeredAbility(final SaffiEriksdotterDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SaffiEriksdotterDelayedTriggeredAbility copy() {
        return new SaffiEriksdotterDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.isControlledBy(game.getOwnerId(event.getTargetId())) && super.checkTrigger(event, game);
    }
}
