package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TishanasTidebinder extends CardImpl {

    public TishanasTidebinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Tishana's Tidebinder enters the battlefield, counter up to one target activated or triggered ability. If an ability of an artifact, creature, or planeswalker is countered this way, that permanent loses all abilities for as long as Tishana's Tidebinder remains on the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TishanasTidebinderEffect());
        ability.addTarget(new TargetActivatedOrTriggeredAbility(0, 1));
        this.addAbility(ability);
    }

    private TishanasTidebinder(final TishanasTidebinder card) {
        super(card);
    }

    @Override
    public TishanasTidebinder copy() {
        return new TishanasTidebinder(this);
    }
}

class TishanasTidebinderEffect extends OneShotEffect {

    TishanasTidebinderEffect() {
        super(Outcome.Benefit);
        staticText = "counter up to one target activated or triggered ability. If an ability of an " +
                "artifact, creature, or planeswalker is countered this way, that permanent loses " +
                "all abilities for as long as {this} remains on the battlefield";
    }

    private TishanasTidebinderEffect(final TishanasTidebinderEffect effect) {
        super(effect);
    }

    @Override
    public TishanasTidebinderEffect copy() {
        return new TishanasTidebinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        if (stackObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(stackObject.getSourceId());
        game.getStack().counter(stackObject.getId(), source, game);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || sourcePermanent == null
                || !permanent.isArtifact(game) && !permanent.isCreature(game) && !permanent.isPlaneswalker(game)) {
            return true;
        }
        game.addEffect(new LoseAllAbilitiesTargetEffect(Duration.UntilSourceLeavesBattlefield)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
