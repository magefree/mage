package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenBloodcaster extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public VoldarenBloodcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.b.BloodbatSummoner.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Voldaren Bloodcaster or another nontoken creature you control dies, create a Blood token.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new BloodToken()), false, filter
        ));

        // Whenever you create a Blood token, if you control five or more Blood tokens, transform Voldaren Bloodcaster.
        this.addAbility(new TransformAbility());
        this.addAbility(new VoldarenBloodcasterTriggeredAbility());
    }

    private VoldarenBloodcaster(final VoldarenBloodcaster card) {
        super(card);
    }

    @Override
    public VoldarenBloodcaster copy() {
        return new VoldarenBloodcaster(this);
    }
}

class VoldarenBloodcasterTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BLOOD);

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Hint hint = new ValueHint("Blood tokens you control", new PermanentsOnBattlefieldCount(filter));

    VoldarenBloodcasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
        this.addHint(hint);
    }

    private VoldarenBloodcasterTriggeredAbility(final VoldarenBloodcasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VoldarenBloodcasterTriggeredAbility copy() {
        return new VoldarenBloodcasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATED_TOKEN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && filter.match(permanent, game) && isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().count(filter, getSourceId(), getControllerId(), game) >= 5;
    }

    @Override
    public String getRule() {
        return "Whenever you create a Blood token, if you control five or more Blood tokens, transform {this}.";
    }
}
