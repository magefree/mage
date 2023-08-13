
package mage.cards.f;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author rscoates
 */
public final class FiresongAndSunspeaker extends CardImpl {

    private static final FilterCard filter = new FilterCard("red instant and sorcery spells you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public FiresongAndSunspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Red instant and sorcery spells you control have lifelink.
        Effect effect = new GainAbilityControlledSpellsEffect(LifelinkAbility.getInstance(), filter);
        effect.setText("Red instant and sorcery spells you control have lifelink");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Whenever a white instant or sorcery spell causes you to gain life, Firesong and Sunspeaker deals 3 damage to target creature or player.
        this.addAbility(new FiresongAndSunspeakerTriggeredAbility());
    }

    private FiresongAndSunspeaker(final FiresongAndSunspeaker card) {
        super(card);
    }

    @Override
    public FiresongAndSunspeaker copy() {
        return new FiresongAndSunspeaker(this);
    }
}

class FiresongAndSunspeakerTriggeredAbility extends TriggeredAbilityImpl {

    public FiresongAndSunspeakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3), false);
        this.addTarget(new TargetCreatureOrPlayer());
    }

    public FiresongAndSunspeakerTriggeredAbility(final FiresongAndSunspeakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FiresongAndSunspeakerTriggeredAbility copy() {
        return new FiresongAndSunspeakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object instanceof Spell) {
            if (event.getTargetId().equals(this.getControllerId())
                    && object.getColor(game).contains(ObjectColor.WHITE)
                    && (object.isInstant(game)
                    || object.isSorcery(game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a white instant or sorcery spell causes you to gain life, Firesong and Sunspeaker deals 3 damage to target creature or player.";
    }
}
