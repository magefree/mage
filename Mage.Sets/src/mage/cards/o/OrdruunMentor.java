package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.MentorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrdruunMentor extends CardImpl {

    public OrdruunMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Mentor
        this.addAbility(new MentorAbility());

        // Whenever you attack a player, target creature that's attacking that player gains first strike until end of turn.
        this.addAbility(new OrdruunMentorTriggeredAbility());
    }

    private OrdruunMentor(final OrdruunMentor card) {
        super(card);
    }

    @Override
    public OrdruunMentor copy() {
        return new OrdruunMentor(this);
    }
}

class OrdruunMentorTriggeredAbility extends TriggeredAbilityImpl {

    private enum OrdruunMentorPredicate implements ObjectSourcePlayerPredicate<Permanent> {
        instance;

        @Override
        public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
            return CardUtil.getEffectValueFromAbility(
                            input.getSource(), "playerAttacked", UUID.class
                    )
                    .filter(uuid -> uuid.equals(game.getCombat().getDefenderId(input.getObject().getId())))
                    .isPresent();
        }
    }

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that's attacking that player");

    static {
        filter.add(OrdruunMentorPredicate.instance);
    }

    OrdruunMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()).setText(""));
        this.setTriggerPhrase("Whenever you attack a player, ");
        this.addTarget(new TargetPermanent(filter));
    }

    private OrdruunMentorTriggeredAbility(final OrdruunMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OrdruunMentorTriggeredAbility copy() {
        return new OrdruunMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || game.getPlayer(event.getTargetId()) == null) {
            return false;
        }
        this.getEffects().setValue("playerAttacked", event.getTargetId());
        return true;
    }
}
