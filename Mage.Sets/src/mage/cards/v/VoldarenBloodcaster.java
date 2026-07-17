package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
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
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenBloodcaster extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("nontoken creature you control");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.BLOOD, "Blood token you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter2.add(TokenPredicate.TRUE);
    }

    public VoldarenBloodcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.WIZARD}, "{1}{B}",
                "Bloodbat Summoner",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE, SubType.WIZARD}, "B"
        );

        // Voldaren Bloodcaster
        this.getLeftHalfCard().setPT(2, 1);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Voldaren Bloodcaster or another nontoken creature you control dies, create a Blood token.
        this.getLeftHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new BloodToken()), false, filter
        ));

        // Whenever you create a Blood token, if you control five or more Blood tokens, transform Voldaren Bloodcaster.
        this.getLeftHalfCard().addAbility(new VoldarenBloodcasterTriggeredAbility());

        // Bloodbat Summoner
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, up to one target Blood token you control becomes a 2/2 black Bat creature with flying and haste in addition to its other types.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(2, 2, "", SubType.BAT)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(HasteAbility.getInstance())
                        .withColor("B"),
                false, false, Duration.Custom
        ).setText("up to one target Blood token you control becomes a " +
                "2/2 black Bat creature with flying and haste in addition to its other types"));
        ability.addTarget(new TargetPermanent(0, 1, filter2));
        this.getRightHalfCard().addAbility(ability);
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
        return game.getBattlefield().count(filter, getControllerId(), this, game) >= 5;
    }

    @Override
    public String getRule() {
        return "Whenever you create a Blood token, if you control five or more Blood tokens, transform {this}.";
    }
}
