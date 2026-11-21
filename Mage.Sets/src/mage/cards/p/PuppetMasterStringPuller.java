package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.OneOrMoreDamagePlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.GoadedPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

public final class PuppetMasterStringPuller extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("goaded creatures");

    static {
        filter.add(GoadedPredicate.instance);
    }

    public PuppetMasterStringPuller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ARTIFICER, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        Ability attackAbility = new AttacksTriggeredAbility(new GoadTargetEffect());
        attackAbility.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        attackAbility.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(attackAbility);

        this.addAbility(new PuppetMasterDamageTriggeredAbility());
    }

    private PuppetMasterStringPuller(final PuppetMasterStringPuller card) {
        super(card);
    }

    @Override
    public PuppetMasterStringPuller copy() {
        return new PuppetMasterStringPuller(this);
    }
}

class PuppetMasterDamageTriggeredAbility extends OneOrMoreDamagePlayerTriggeredAbility {

    PuppetMasterDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()), PuppetMasterStringPuller.filter,
                true, false, SetTargetPointer.NONE, false);
        setTriggerPhrase("Whenever one or more goaded creatures deal combat damage to one of your opponents, "
                + "create a Treasure token");
    }

    private PuppetMasterDamageTriggeredAbility(final PuppetMasterDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getPlayerId())
                && super.checkEvent(event, game);
    }

    @Override
    public PuppetMasterDamageTriggeredAbility copy() {
        return new PuppetMasterDamageTriggeredAbility(this);
    }
}
