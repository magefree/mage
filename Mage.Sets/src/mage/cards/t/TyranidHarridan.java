package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TyranidGargoyleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyranidHarridan extends CardImpl {

    public TyranidHarridan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{4}")));

        // Shrieking Gargoyles -- Whenever Tyranid Harridan or another Tyranid you control deals combat damage to a player, create a 1/1 blue Tyranid Gargoyle creature token with flying.
        this.addAbility(new TyranidHarridanTriggeredAbility());
    }

    private TyranidHarridan(final TyranidHarridan card) {
        super(card);
    }

    @Override
    public TyranidHarridan copy() {
        return new TyranidHarridan(this);
    }
}

class TyranidHarridanTriggeredAbility extends DealsDamageToAPlayerAllTriggeredAbility {

    private static final FilterPermanent filter = new FilterControlledPermanent(
            SubType.TYRANID, "{this} or another Tyranid you control"
    );

    TyranidHarridanTriggeredAbility() {
        super(new CreateTokenEffect(new TyranidGargoyleToken()), filter, false, SetTargetPointer.NONE, true);
        this.setTriggerPhrase("Shrieking Gargoyles");
    }

    private TyranidHarridanTriggeredAbility(final TyranidHarridanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TyranidHarridanTriggeredAbility copy() {
        return new TyranidHarridanTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game)
                && event.getSourceId().equals(this.getSourceId());
    }
}
