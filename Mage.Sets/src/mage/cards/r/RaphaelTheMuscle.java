package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MutagenToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelTheMuscle extends CardImpl {

    public RaphaelTheMuscle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Double all damage that creatures you control with counters on them would deal.
        this.addAbility(new SimpleStaticAbility(new RaphaelTheMuscleReplacementEffect()));

        // When Raphael enters, create a Mutagen token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MutagenToken())));

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private RaphaelTheMuscle(final RaphaelTheMuscle card) {
        super(card);
    }

    @Override
    public RaphaelTheMuscle copy() {
        return new RaphaelTheMuscle(this);
    }
}

class RaphaelTheMuscleReplacementEffect extends ReplacementEffectImpl {

    RaphaelTheMuscleReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "double all damage that creatures you control with counters on them would deal";
    }

    private RaphaelTheMuscleReplacementEffect(final RaphaelTheMuscleReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RaphaelTheMuscleReplacementEffect copy() {
        return new RaphaelTheMuscleReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && permanent.getCounters(game).getTotalCount() > 0;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
